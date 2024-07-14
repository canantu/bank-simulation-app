package com.cydeo.controller;

import com.cydeo.dto.AccountDTO;
import com.cydeo.enums.AccountType;
import com.cydeo.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

        model.addAttribute("accountDTO", new AccountDTO());
        model.addAttribute("accountTypes", AccountType.values());

        return "/account/create-account";
    }

    @PostMapping("/create")
    public String createAccount(@Valid @ModelAttribute("account") AccountDTO accountDTO, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("accountTypes", AccountType.values());
            return "/account/create-account";
        }

        System.out.println(accountDTO);
        accountService.createNewAccount(accountDTO);
   //     System.out.println(newAccountDTO);
        return "redirect:/index";
    }
    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable("id") Long id){

        accountService.deleteByID(id);
        return "redirect:/index";
    }

    @GetMapping("/activate/{id}")
    public String activateAccount(@PathVariable("id") Long id){

        accountService.activateByID(id);
        return "redirect:/index";
    }




}
