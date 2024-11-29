package com.translation_project.mvp.service;

import com.translation_project.mvp.model.User;
import com.translation_project.mvp.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User addUser(User u) {
        return userRepository.save(u);
    }

    public User modifyUser(User u) {
        User userModified = findUserById(u.getId());

        userModified.setFirstName(u.getFirstName());
        userModified.setLastName(u.getLastName());
        userModified.setEmail(u.getEmail());
        userModified.setPassword(u.getPassword());
        userModified.setRole(u.getRole());

        return userRepository.save(userModified);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
