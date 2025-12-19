package com.javaLab.web.services;

import com.javaLab.web.dto.ServerTimeDTO;
import com.javaLab.web.dto.UserEditDTO;
import com.javaLab.web.dto.UserResponseDTO;
import com.javaLab.web.exceptions.TimeServiceException;
import com.javaLab.web.exceptions.UnauthorizedException;
import com.javaLab.web.exceptions.UserNotFoundException;
import com.javaLab.web.models.User;
import com.javaLab.web.repository.UserRepository;
import com.javaLab.web.utils.Mapper;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final Mapper mapper;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm:ss");

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()
                || "anonymousUser".equals(auth.getPrincipal())) {
            throw new UnauthorizedException("User not logged in");
        }
        return auth.getName();
    }

    private User getCurrentUser() {
        String username = getCurrentUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public ResponseEntity<UserResponseDTO> getProfile(HttpSession session) {
        User user = getCurrentUser();

        return ResponseEntity.ok(mapper.userToUserResponseDTO(user));
    }

    public ServerTimeDTO getServerTime() {
        try {
            String time = FORMATTER.format(LocalDateTime.now());
            return new ServerTimeDTO(time);
        } catch (Exception e) {
            throw new TimeServiceException("Не удалось получить серверное время", e);
        }
    }

    public ResponseEntity<String> uploadAvatar(HttpSession session, MultipartFile file) {
        User user = getCurrentUser();

        String avatarUrl = mapper.processAvatar(file, user.getUsername());
        user.setAvatar(avatarUrl);
        userRepository.save(user);

        return ResponseEntity.ok("Avatar uploaded");
    }

    public ResponseEntity<String> deleteAvatar(HttpSession session) {
        User user = getCurrentUser();

        user.setAvatar(mapper.getDefaultImageUrl());
        userRepository.save(user);

        return ResponseEntity.ok("Avatar deleted");
    }

    public ResponseEntity<String> editUser(HttpSession session, UserEditDTO dto) {
        User user = getCurrentUser();

        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
            user.setUsername(dto.getUsername());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            if (mapper.isValidEmail(dto.getEmail())) {
                user.setEmail(dto.getEmail());
            }
            else return ResponseEntity.badRequest().body("Неправильный формат почты");

        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getAvatar() != null && !dto.getAvatar().isEmpty()) {
            if (user.getAvatar() != null && !user.getAvatar().isBlank()
                    && !user.getAvatar().equals(mapper.getDefaultImageUrl())) {
                mapper.deleteAvatar(user.getAvatar());
            }
            String newAvatarUrl = mapper.processAvatar(dto.getAvatar(), user.getUsername());
            user.setAvatar(newAvatarUrl);
        }

        userRepository.save(user);

        return ResponseEntity.ok("User updated");
    }
}
