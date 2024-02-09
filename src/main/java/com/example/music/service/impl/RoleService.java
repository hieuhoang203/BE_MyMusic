package com.example.music.service.impl;

import com.example.music.entity.Role;
import com.example.music.repositories.RoleRepository;
import com.example.music.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RoleService implements IService <Role, Byte> {

    @Autowired
    private RoleRepository repository;

    @Override
    public Role insert(Role role) {
        this.repository.save(role);
        return role;
    }

    @Override
    public Role update(Byte id, Role role) {
        this.repository.save(role);
        return role;
    }

    @Override
    public Role delete(Byte id) {
        this.repository.updateStatus(id);
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Role detail(Byte id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Map<Byte, Role> select(String status) {
        Map<Byte, Role> roleMap;
        roleMap = this.repository.select(status).stream().collect(Collectors.toMap(Role::getId, Function.identity()));
        return roleMap;
    }

}
