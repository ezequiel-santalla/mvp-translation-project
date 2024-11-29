package com.translation_project.mvp.service;

import com.translation_project.mvp.model.User;

import java.util.List;

public interface IUserService {
    List<User> findUsers();
    User addUser(User u);
    User modifyUser(User u);
    void deleteUser(Long id);
    User findUserById(Long id);
}
