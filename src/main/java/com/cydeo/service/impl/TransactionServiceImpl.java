package com.cydeo.service.impl;

import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.TransactionService;
import com.cydeo.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {

    public TransactionServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private final AccountRepository accountRepository;
    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date dateCreated, String message) {
        /*
        - if sender or receiver is null
        - if sender and receiver is the same account?
        - if sender has enough balance to make transfer?
        - if both accounts are checking, if not, one of them savings, it needs to be same userId
         */

        validateAccount(sender, receiver);


        return null;
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
    public List<Transaction> findAll() {
        return null;
    }
}
