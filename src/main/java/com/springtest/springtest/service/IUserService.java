package com.springtest.springtest.service;

import com.springtest.springtest.model.User;

import java.util.List;

public interface IUserService {

    User getOneUserById(Long id);
    List<User> getAllUsers();
    User createUser(User user);
    User updateUser(Long userId, User updatedUser);
    boolean deleteUserById(Long id);
}
