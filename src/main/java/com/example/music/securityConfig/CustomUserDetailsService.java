package com.example.music.securityConfig;

import com.example.music.entity.Account;
import com.example.music.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.repository.loginAccount(username).orElseThrow(() -> new UsernameNotFoundException("User name not found!"));
        return User.builder()
                .username(account.getLogin())
                .password(account.getPass())
                .roles(String.valueOf(account.getRole()))
                .build();
    }

}
