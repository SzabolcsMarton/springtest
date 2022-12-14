package com.springtest.springtest.service;

import com.springtest.springtest.helpers.Validator;
import com.springtest.springtest.model.User;
import com.springtest.springtest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User getOneUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cannot find user with id: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User createUser(User user) throws IllegalArgumentException{
        Validator.validateUser(user);
        String name = user.getName();
        if (repository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("User already exists with the name: " + name);
        }

        return repository.save(user);
    }

    @Override
    public User updateUser(Long userId, User updatedUser) {
        User userToUpdate = repository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Cannot find user with id: " + userId));
        userToUpdate.setName(updatedUser.getName());
        userToUpdate.setAge(userToUpdate.getAge());

        return repository.save(userToUpdate);
    }

    @Override
    public boolean deleteUserById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
