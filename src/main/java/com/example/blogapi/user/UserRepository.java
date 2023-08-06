package com.example.blogapi.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity>findByUsernameOrEmail(String username,String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
