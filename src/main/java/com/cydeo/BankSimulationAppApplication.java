package com.cydeo;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.service.impl.AccountServiceImpl;
import com.cydeo.service.impl.TransactionServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class BankSimulationAppApplication {

    public static void main(String[] args) {

        ApplicationContext container = SpringApplication.run(BankSimulationAppApplication.class, args);
        AccountServiceImpl accountService = container.getBean(AccountServiceImpl.class);
        TransactionServiceImpl transactionService = container.getBean(TransactionServiceImpl.class);

        Account sender = accountService.createNewAccount(BigDecimal.valueOf(70), new Date(), AccountType.CHECKING, 1L);
        Account receiver = accountService.createNewAccount(BigDecimal.valueOf(30), new Date(), AccountType.CHECKING, 2L);

        accountService.listAllAccounts().forEach(System.out::println);

        transactionService.makeTransfer(sender, receiver, new BigDecimal(10), new Date(), "Transaction 1");

        accountService.listAllAccounts().forEach(System.out::println);

        System.out.println(transactionService.findAllTransactions().get(0));


    }

}
