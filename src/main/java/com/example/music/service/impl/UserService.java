package com.example.music.service.impl;

import com.example.music.entity.User;
import com.example.music.repositories.UserRepository;
import com.example.music.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService implements IService<User, Long> {

    @Autowired
    private UserRepository repository;

    @Override
    public User insert(User user) {
        return this.repository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        return this.repository.save(user);
    }

    @Override
    public User delete(Long id) {
        this.repository.updateStatus(id);
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public User detail(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Map<Long, User> select(String status) {
        Map<Long, User> userMap = new HashMap<>();
        this.repository.select(status).stream().collect(Collectors.toMap(User::getId, Function.identity()));
        return userMap;
    }

}
