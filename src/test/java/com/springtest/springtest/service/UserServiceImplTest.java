package com.springtest.springtest.service;

import com.springtest.springtest.model.User;
import com.springtest.springtest.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    UserServiceImpl service;

    @Test
    void getOneUserById_should_be_success_test() {
        User user = new User(1L, "Sanyi", 25);
        when(repository.findById(1L)).thenReturn(Optional.of(user));

        User testUser = service.getOneUserById(1L);

        assertEquals("Sanyi", testUser.getName());
        assertEquals(25, testUser.getAge());

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getOneUserById_should_throw_exception_test() {
        when(repository.findById(2L)).thenThrow(new IllegalArgumentException("Cannot find user with id: " + 2L));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.getOneUserById(2L);
        }, "Cannot find user with id: " + 2L);

        verify(repository, times(1)).findById(2L);
    }


    @Test
    void getAllUsers_test_should_be_success_test() {
        User user1 = new User(1L, "Sanyi", 25);
        User user2 = new User(2L, "Pisti", 35);
        User user3 = new User(3L, "Klárika", 45);

        List<User> users = new ArrayList<>();

        users.add(user1);
        users.add(user2);
        users.add(user3);

        when(repository.findAll()).thenReturn(users);

        List<User> testUsers = service.getAllUsers();

        assertEquals(3, testUsers.size());
        assertEquals(45, testUsers.get(2).getAge());

        verify(repository, times(1)).findAll();
    }

    @Test
    void createUser_should_success_and_return_the_user_test() {
        User user = new User(1L, "Sanyi", 25);

        when(repository.save(user)).thenReturn(user);
        when(repository.findByName(anyString())).thenReturn(Optional.empty());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        User testUser = service.createUser(user);

        verify(repository, times(1)).save(user);
        verify(repository, times(1)).findByName(anyString());
        verify(repository).save(userCaptor.capture());
        verify(repository).save(argThat(argument -> argument.getName().equals("Sanyi")));

        assertEquals("Sanyi", testUser.getName());
        assertEquals(25, testUser.getAge());

    }

    @Test
    void createUser_should_fail_and_throw_IllegalArgument_Exception_test() {
        User user = new User(1L, "Sanyi", 25);

        when(repository.findByName(anyString())).thenReturn(Optional.of(user));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.createUser(user);
        }, "User already exists with the name: " + user.getName());

    }

    @Test
    void updateUser_should_success_test() {
        User user = new User(1L, "Sanyi", 25);
        User updatedUser = new User(1L, "Pisti", 25);

        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(repository.save(any())).thenReturn(updatedUser);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        User resultUser = service.updateUser(1L,updatedUser);

        assertEquals("Pisti", resultUser.getName());
        assertEquals(25, resultUser.getAge());

        verify(repository, times(1)).save(any());
        verify(repository).save(argThat(argument -> argument.getName().equals("Pisti")));
    }

    @Test
    void updateUser_should_fail_throw_exception_test() {
        User user = new User(2L, "Sanyi", 25);
        when(repository.findById(2L)).thenThrow(new IllegalArgumentException("Cannot find user with id: " + 2L));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.updateUser(2L,user);
        }, "Cannot find user with id: " + 2L);

        verify(repository, never()).save(any());

    }

    @Test
    void deleteUserById_should_succes_return_true_test() {
        when(repository.existsById(anyLong())).thenReturn(true);

        boolean result = service.deleteUserById(1L);

        assertTrue(result);
        verify(repository, times(1)).existsById(anyLong());
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteUserById_should_fail_return_false_test() {
        when(repository.existsById(anyLong())).thenReturn(false);

        boolean result = service.deleteUserById(1L);

        assertFalse(result);
        verify(repository, times(1)).existsById(anyLong());

    }
}