package com.cydeo.service.impl;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userId) {
        // we need to create the Account object
        Account account = Account.builder().id(UUID.randomUUID())
                .balance(balance).accountType(accountType)
                .creationDate(createDate).userId(userId).accountStatus(AccountStatus.ACTIVE)
                .build();
        // save into the DB (repository)
        // return the object created
        return accountRepository.saveAccount(account);
    }

    @Override
    public List<Account> listAllAccounts() {
        return accountRepository.findAll();
    }
}
