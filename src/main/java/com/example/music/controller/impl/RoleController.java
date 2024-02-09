package com.example.music.controller.impl;

import com.example.music.controller.IController;
import com.example.music.entity.Role;
import com.example.music.service.impl.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/role")
public class RoleController implements IController<Role, Byte> {

    @Autowired
    private RoleService roleService;

    @Override
    @GetMapping(value = "")
    public Map<Byte, Role> select(@RequestParam(value = "status", defaultValue = "DANG_HOAT_DONG", required = false) String status) {
        return this.roleService.select(status);
    }

    @Override
    @PostMapping(value = "/save")
    public Role insert(@RequestBody Role role) {
        return this.roleService.insert(role);
    }

    @Override
    @PutMapping(value = "/update/{id}")
    public Role update(@PathVariable Byte id, @RequestBody Role role) {
        return this.roleService.update(id, role);
    }

    @Override
    @DeleteMapping(value = "/delete/{id}")
    public Role delete(@PathVariable Byte id) {
        return this.roleService.delete(id);
    }

    @Override
    @GetMapping(value = "/search/{id}")
    public Role search(@PathVariable Byte id) {
        return this.roleService.detail(id);
    }

}
