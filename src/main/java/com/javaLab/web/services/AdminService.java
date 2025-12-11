package com.javaLab.web.services;

import com.javaLab.web.configs.ImageConfig;
import com.javaLab.web.dto.AdminCreateUserDTO;
import com.javaLab.web.dto.AdminEditUserDTO;
import com.javaLab.web.exceptions.UserNotFoundException;
import com.javaLab.web.models.User;
import com.javaLab.web.repository.UserRepository;
import com.javaLab.web.utils.Mapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    private final Mapper mapper;

    private final ImageConfig config;

    public ResponseEntity<String> updateUser(Long id, AdminEditUserDTO dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
            user.setUsername(dto.getUsername());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            user.setEmail(dto.getEmail());
        }

        if (dto.getAvatar() != null && !dto.getAvatar().isEmpty()) {
            String avatarUrl = mapper.processAvatar(dto.getAvatar(), user.getUsername());
            user.setAvatar(avatarUrl);
        }

        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }

        userRepository.save(user);

        return ResponseEntity.ok("User updated by admin");
    }

    @Transactional
    public ResponseEntity<String> deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        mapper.deleteAvatar(user.getAvatar());
        userRepository.delete(user);

        return ResponseEntity.ok("User deleted");
    }



    @Transactional
    public ResponseEntity<String> createUser(AdminCreateUserDTO dto) {

        Optional<String> error = checkIfUserExists(dto.getUsername(), dto.getEmail());
        if (error.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error.get());
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole() != null ? dto.getRole() : com.javaLab.web.models.Role.VISITOR);

        if (dto.getAvatar() != null && !dto.getAvatar().isEmpty()) {
            String avatarUrl = mapper.processAvatar(dto.getAvatar(), dto.getUsername());
            user.setAvatar(avatarUrl);
        } else {
            user.setAvatar(mapper.getDefaultImageUrl());
        }

        userRepository.save(user);

        return ResponseEntity.ok("User created successfully");
    }

    private Optional<String> checkIfUserExists(String username, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            return Optional.of("Пользователь с таким логином уже зарегистрирован");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            return Optional.of("Пользователь с такой почтой уже зарегистрирован");
        }
        return Optional.empty();

    }

    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    public ResponseEntity<User> getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return ResponseEntity.ok(user);
    }

}