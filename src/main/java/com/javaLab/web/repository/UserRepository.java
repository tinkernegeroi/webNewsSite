package com.javaLab.web.repository;

import com.javaLab.web.models.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByUsername(String username);

    Optional<UserModel> findByEmail(String email);
}
