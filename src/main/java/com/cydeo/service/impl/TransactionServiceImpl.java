package com.cydeo.service.impl;

import com.cydeo.dto.AccountDTO;
import com.cydeo.dto.TransactionDTO;
import com.cydeo.entity.Transaction;
import com.cydeo.enums.AccountType;
import com.cydeo.exception.AccountOwnershipException;
import com.cydeo.exception.BalanceNotSufficientException;
import com.cydeo.exception.UnderConstructionException;
import com.cydeo.mapper.TransactionMapper;
import com.cydeo.repository.AccountRepository;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import com.cydeo.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Value("${under_construction}")
    private boolean underConstruction;
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(AccountService accountService, TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public TransactionDTO makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date dateCreated, String message) {
        /*
        - if sender or receiver is null
        - if sender and receiver is the same account?
        - if sender has enough balance to make transfer?
        - if both accounts are checking, if not, one of them savings, it needs to be same userId
         */

        if (!underConstruction) {
            validateAccount(sender, receiver);
            checkAccountOwnership(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);

        /*
            After all validations are completed and transfer is done, we need Transaction object and save/return it.
         */

            TransactionDTO transactionDTO = new TransactionDTO(sender, receiver, amount, message, dateCreated);

            // save into db and return
            transactionRepository.save(transactionMapper.convertToEntity(transactionDTO));
            return transactionDTO;
        }else {
            throw new UnderConstructionException("App is under construction, please try it later.");
        }

    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, AccountDTO sender, AccountDTO receiver) {

        if (checkSenderBalance(amount, sender)){
            //update sender and receiver
            //100 - 80  = 20
            sender.setBalance(sender.getBalance().subtract(amount));
            //50 + 80 = 130
            receiver.setBalance(receiver.getBalance().add(amount));
            /*
                get the dto from the database for both sender and receiver, update balance and save it
                create accountService updateAccount method and use it for saving
             */
            //find accounts by id
            AccountDTO senderAcc = accountService.findById(sender.getId());
            senderAcc.setBalance(sender.getBalance());
            accountService.updateAccount(senderAcc);

            AccountDTO receiverAcc = accountService.findById(receiver.getId());
            receiverAcc.setBalance(receiver.getBalance());
            accountService.updateAccount(receiverAcc);

        }else{
            throw new BalanceNotSufficientException("Balance is not enough to complete the transfer");
        }
    }

    private boolean checkSenderBalance(BigDecimal amount, AccountDTO sender) {
        return sender.getBalance().compareTo(amount) >= 0;
    }


    private void checkAccountOwnership(AccountDTO sender, AccountDTO receiver) {

        /*
            write an if statement that checks if one of the accounts is saving,
            and user of the saver and checking are not same, throw AccountOwnershipException
         */

        if ( (sender.getAccountType()== AccountType.SAVING || receiver.getAccountType().equals(AccountType.SAVING))
                && !sender.getUserId().equals(receiver.getUserId()) ){
            throw new AccountOwnershipException("Transfer from a saving account can not be done to a different user's account.");
        }
    }

    private void validateAccount(AccountDTO sender, AccountDTO receiver) {
        /*
        - if any of the account is null
        - if account ids are the same
        - if account exists
         */

        if (sender==null || receiver==null){
            throw new BadRequestException("Sender or receiver can not be null");
        }
        // if sender and receiver are the same, throw BadRequestException
        if (sender.getId().equals(receiver.getId())){
            throw new BadRequestException("Sender account must be different than the receiver account");
        }

        findAccountById(sender.getId());
        findAccountById(receiver.getId());



    }

    private void findAccountById(Long id) {
        accountService.findById(id);
    }

    @Override
    public List<TransactionDTO> findAllTransactions() {

        List<Transaction> transactionList = transactionRepository.findAll();
        return transactionList.stream().map(transactionMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findLast10Transactions() {
        List<Transaction> transactionList = transactionRepository.findLast10Transactions();
        return transactionList.stream().map(transactionMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findTransactionListByAccountId(Long id) {
        List<Transaction> transactionList = transactionRepository.findTransactionListByAccountId(id);
        return transactionList.stream().map(transactionMapper::convertToDTO).collect(Collectors.toList());

    }


}
