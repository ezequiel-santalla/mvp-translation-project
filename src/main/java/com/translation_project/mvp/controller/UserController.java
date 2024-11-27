package com.translation_project.mvp.controller;

import com.translation_project.mvp.model.User;
import com.translation_project.mvp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.findUsers();
    }

    @PostMapping
    public User postUser(@RequestBody User u) {
        return userService.addUser(u);
    }

    @PutMapping("/{id}")
    public User putUser(@PathVariable Long id, @RequestBody User u) {
        u.setId(id);

        return userService.modifyUser(u);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }
}
