package com.cydeo.repository;

import com.cydeo.dto.TransactionDTO;
import com.cydeo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
/*
    public static List<TransactionDTO> transactionDTOList = new ArrayList<>();

    public TransactionDTO save(TransactionDTO transactionDTO) {
        transactionDTOList.add(transactionDTO);
        return transactionDTO;
    }

    public List<TransactionDTO> findAll(){
        return transactionDTOList;
    }

    public List<TransactionDTO> findLast10Transactions() {


        return transactionDTOList.stream().sorted(Comparator.comparing(TransactionDTO::getCreateDate).reversed())
                .limit(10).collect(Collectors.toList());
    }

    public List<TransactionDTO> findTransactionListByAccountId(Long id) {
        return transactionDTOList.stream()
                .filter(transactionDTO -> (transactionDTO.getReceiver().equals(id) || transactionDTO.getSender().equals(id)))
                .collect(Collectors.toList());
    }


 */



}
