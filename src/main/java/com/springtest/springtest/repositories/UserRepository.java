package com.springtest.springtest.repositories;

import com.springtest.springtest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
