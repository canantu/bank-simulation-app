package com.cydeo.service;

import com.cydeo.dto.AccountDTO;
import com.cydeo.dto.TransactionDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionDTO makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date dateCreated, String message);

    List<TransactionDTO> findAllTransactions();

    List<TransactionDTO> findLast10Transactions();

    List<TransactionDTO> findTransactionListByAccountId(Long id);
}
