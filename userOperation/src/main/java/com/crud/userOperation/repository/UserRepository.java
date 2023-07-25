package com.crud.userOperation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.userOperation.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}
