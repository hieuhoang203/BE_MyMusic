package com.example.music.controller.impl;

import com.example.music.controller.IController;
import com.example.music.entity.User;
import com.example.music.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController implements IController<User, Long> {
    
    @Autowired
    private UserService userService;
    
    @Override
    @GetMapping(value = "")
    public Map<Long, User> select(@RequestParam(value = "status", defaultValue = "Activate") String status) {
        return this.userService.select(status);
    }

    @Override
    @PostMapping(value = "/save")
    public User insert(@RequestBody User user) {
        return this.userService.insert(user);
    }

    @Override
    @PutMapping(value = "/update/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        return this.userService.update(id, user);
    }

    @Override
    @DeleteMapping(value = "/delete/{id}")
    public User delete(@PathVariable Long id) {
        return this.userService.delete(id);
    }

    @Override
    @GetMapping(value = "/search/{id}")
    public User search(@PathVariable Long id) {
        return this.userService.detail(id);
    }
    
}
