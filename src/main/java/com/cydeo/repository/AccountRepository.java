package com.cydeo.repository;

import com.cydeo.entity.Account;
import com.cydeo.exception.RecordNotFoundException;
import com.cydeo.dto.AccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
/*
    public static List<AccountDTO> accountDTOList = new ArrayList<>();
    public AccountDTO saveAccount(AccountDTO accountDTO){
        accountDTOList.add(accountDTO);
        return accountDTO;
    }

    public List<AccountDTO> findAll() {
        return accountDTOList;
    }

    public AccountDTO findById(Long id) {
        // find the account inside the account list, if it is not available throw exception
        return accountDTOList.stream()
                .filter(p -> p.getId().equals(id))
                .findAny()
                .orElseThrow(()-> new RecordNotFoundException("Account does not exist in the database") );
    }
*/




}
