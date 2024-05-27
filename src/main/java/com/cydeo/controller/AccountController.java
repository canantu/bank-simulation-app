package com.cydeo.controller;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/index")
    public String getIndexPage(Model model){

        model.addAttribute("accountList", accountService.listAllAccounts());
        return "account/index";
    }

    @GetMapping("/create-form")
    public String getCreateFormPage(Model model){

        model.addAttribute("account", Account.builder().build());
        model.addAttribute("accountTypes", AccountType.values());

        return "/account/create-account";
    }

    @PostMapping("/create")
    public String createAccount(@Valid @ModelAttribute("account") Account account){

        System.out.println(account);
        Account newAccount = accountService.createNewAccount(account.getBalance(), new Date(), account.getAccountType(), account.getUserId());
        System.out.println(newAccount);
        return "redirect:/index";
    }
    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable("id") UUID id){

        accountService.deleteByID(id);
        return "redirect:/index";
    }

    @GetMapping("/activate/{id}")
    public String activateAccount(@PathVariable("id") UUID id){

        accountService.activateByID(id);
        return "redirect:/index";
    }




}
