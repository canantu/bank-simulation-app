package com.cydeo.repository;

import com.cydeo.enums.AccountType;
import com.cydeo.exception.RecordNotFoundException;
import com.cydeo.model.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AccountRepository {

    public static List<Account> accountList = new ArrayList<>();
    public Account saveAccount(Account account){
        accountList.add(account);
        return account;
    }

    public List<Account> findAll() {
        return accountList;
    }

    public Account findById(UUID id) {
        // find the account inside the account list, if it is not available throw exception
        return accountList.stream()
                .filter(p -> p.getId().equals(id))
                .findAny()
                .orElseThrow(()-> new RecordNotFoundException("Account does not exist in the database") );
    }


}
