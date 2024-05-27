package com.cydeo.service.impl;

import com.cydeo.enums.AccountType;
import com.cydeo.exception.AccountOwnershipException;
import com.cydeo.exception.BalanceNotSufficientException;
import com.cydeo.exception.UnderConstructionException;
import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.repository.AccountRepository;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.service.TransactionService;
import com.cydeo.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Value("${under_construction}")
    private boolean underConstruction;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date dateCreated, String message) {
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

            Transaction transaction = Transaction.builder().amount(amount).sender(sender.getId()).receiver(receiver.getId())
                    .message(message).createDate(dateCreated).build();

            // save into db and return
            return transactionRepository.save(transaction);
        }else {
            throw new UnderConstructionException("App is under construction, please try it later.");
        }

    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, Account sender, Account receiver) {

        if (checkSenderBalance(amount, sender)){
            //update sender and receiver
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
        }else{
            throw new BalanceNotSufficientException("Balance is not enough to complete the transfer");
        }
    }

    private boolean checkSenderBalance(BigDecimal amount, Account sender) {
        return sender.getBalance().compareTo(amount) >= 0;
    }


    private void checkAccountOwnership(Account sender, Account receiver) {

        /*
            write an if statement that checks if one of the accounts is saving,
            and user of the saver and checking are not same, throw AccountOwnershipException
         */

        if ( (sender.getAccountType()== AccountType.SAVING || receiver.getAccountType().equals(AccountType.SAVING))
                && !sender.getUserId().equals(receiver.getUserId()) ){
            throw new AccountOwnershipException("Transfer from a saving account can not be done to a different user's account.");
        }
    }

    private void validateAccount(Account sender, Account receiver) {
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

    private void findAccountById(UUID id) {
        accountRepository.findById(id);
    }

    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> findLast10Transactions() {
        return transactionRepository.findLast10Transactions();
    }

    @Override
    public Transaction createNewTransaction(UUID sender, UUID receiver, BigDecimal amount, String message, Date date) {
        Transaction transaction = Transaction.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(amount)
                .message(message)
                .createDate(date)
                .build();
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findTransactionListByAccountId(UUID id) {
        return transactionRepository.findTransactionListByAccountId(id);
    }


}
