package com.javaLab.web.services;

import com.javaLab.web.dto.AdminEditUserDTO;
import com.javaLab.web.exceptions.UserNotFoundException;
import com.javaLab.web.models.User;
import com.javaLab.web.repository.UserRepository;
import com.javaLab.web.utils.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    private final Mapper mapper;

    public ResponseEntity<String> updateUser(String username, AdminEditUserDTO dto) {

        User user = userRepository.findByUsername(username)
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
}
