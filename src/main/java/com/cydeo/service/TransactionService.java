package com.cydeo.service;

import com.cydeo.model.Account;
import com.cydeo.model.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date dateCreated, String message);

    List<Transaction> findAllTransactions();

    List<Transaction> findLast10Transactions();

    Transaction createNewTransaction(UUID sender, UUID receiver, BigDecimal amount, String message, Date date);
}
