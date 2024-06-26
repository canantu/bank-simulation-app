package com.cydeo.controller;

import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class TransactionController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    public TransactionController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/make-transfer")
    public String getMakeTransfer(Model model){

        // provide empty transaction object
        model.addAttribute("transaction", Transaction.builder().build());
        // provide list of all accounts
        model.addAttribute("accounts", accountService.listAllAccounts());
        // provide list of last 10 transactions that will be displayed on the table
        model.addAttribute("lastTransactions", transactionService.findLast10Transactions());

        return "transaction/make-transfer";
    }

    @PostMapping("/transfer")
    public String makeTransfer(@Valid  @ModelAttribute("transaction") Transaction transaction, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("accounts", accountService.listAllAccounts());
            model.addAttribute("lastTransactions", transactionService.findLast10Transactions());

            return "transaction/make-transfer";
        }

        // i have UUID of accounts but i need to provide account object.
        // I need to find accounts based on the ID that i have and use as a parameter to complete make transfer
        Account sender = accountService.findById(transaction.getSender());
        Account receiver = accountService.findById(transaction.getReceiver());
        transactionService.makeTransfer(sender, receiver, transaction.getAmount(), new Date(), transaction.getMessage());

        return "redirect:/make-transfer";
    }

    @GetMapping("/transaction/{id}")
    public String getTransactionList(@PathVariable("id") UUID id, Model model){
        List<Transaction> transactionList = transactionService.findTransactionListByAccountId(id);
        model.addAttribute("transactions", transactionList );
        model.addAttribute("accountId", id);
        Account account = accountService.findById(id);
        model.addAttribute("userId", account.getUserId());
        return "transaction/transactions";
    }


}
