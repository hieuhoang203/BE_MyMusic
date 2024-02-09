package com.example.music.service.impl;

import com.example.music.entity.Account;
import com.example.music.repositories.AccountRepository;
import com.example.music.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AccountService implements IService<Account, String> {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account insert(Account account) {
        this.accountRepository.save(account);
        return account;
    }

    @Override
    public Account update(String userName, Account account) {
        this.accountRepository.save(account);
        return account;
    }

    @Override
    public Account delete(String userName) {
        this.accountRepository.updateStatus(userName);
        return this.accountRepository.findById(userName).orElse(null);
    }

    @Override
    public Account detail(String userName) {
        return this.accountRepository.findById(userName).orElse(null);
    }

    @Override
    public Map<String, Account> select(String status) {
        return this.accountRepository.select(status).stream().collect(Collectors.toMap(Account::getUser_name, Function.identity()));
    }
}
