package com.example.music.controller.impl;

import com.example.music.controller.IController;
import com.example.music.entity.Account;
import com.example.music.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/account")
public class AccountController implements IController<Account, String> {

    @Autowired
    private AccountService accountService;

    @Override
    @GetMapping(value = "")
    public Map<String, Account> select(@RequestParam(value = "status", defaultValue = "DANG_HOAT_DONG", required = false) String status) {
        return this.accountService.select(status);
    }

    @Override
    @PostMapping(value = "/save")
    public Account insert(@RequestBody Account account) {
        return this.accountService.insert(account);
    }

    @Override
    @PutMapping(value = "/update/{userName}")
    public Account update(@PathVariable String userName, @RequestBody Account account) {
        return this.accountService.update(userName, account);
    }

    @Override
    @DeleteMapping(value = "/delete/{userName}")
    public Account delete(@PathVariable String userName) {
        return this.accountService.delete(userName);
    }

    @Override
    @GetMapping(value = "/search/{userName}")
    public Account search(@PathVariable String userName) {
        return this.accountService.detail(userName);
    }
}
