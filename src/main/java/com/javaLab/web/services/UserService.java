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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final Mapper mapper;

    private static final String SESSION_USERNAME = "user";

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm:ss");

    private String getUsernameFromSession(HttpSession session) {
        String username = (String) session.getAttribute(SESSION_USERNAME);
        if (username == null) {
            throw new UnauthorizedException("User not logged in");
        }
        return username;
    }

    public ResponseEntity<UserResponseDTO> getProfile(HttpSession session) {
        String username = getUsernameFromSession(session);
        if (session.getAttribute("profileVisited") == null) {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            user.setVisitsCount(user.getVisitsCount() + 1);
            userRepository.save(user);

            session.setAttribute("profileVisited", true);
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return ResponseEntity.ok(mapper.userToUserResponseDTO(user));
    }

    public ServerTimeDTO getServerTime(){
        try {
            String time = FORMATTER.format(LocalDateTime.now());
            return new ServerTimeDTO(time);
        } catch (Exception e) {
            throw new TimeServiceException("Не удалось получить серверное время", e);
        }
    }

    public ResponseEntity<String> uploadAvatar(HttpSession session, MultipartFile file) {
        String username = getUsernameFromSession(session);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String avatarUrl = mapper.processAvatar(file, username);
        user.setAvatar(avatarUrl);
        userRepository.save(user);

        return ResponseEntity.ok("Avatar uploaded");
    }

    public ResponseEntity<String> deleteAvatar(HttpSession session) {
        String username = getUsernameFromSession(session);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setAvatar(mapper.getDefaultImageUrl());
        userRepository.save(user);

        return ResponseEntity.ok("Avatar deleted");
    }

    public ResponseEntity<String> editUser(HttpSession session, UserEditDTO dto) {
        String sessionUsername = getUsernameFromSession(session);

        User user = userRepository.findByUsername(sessionUsername)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
            user.setUsername(dto.getUsername());
            session.setAttribute("user", dto.getUsername());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            user.setEmail(dto.getEmail());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(dto.getPassword());
        }

        if (dto.getAvatar() != null && !dto.getAvatar().isEmpty()) {
            if (!user.getAvatar().isBlank()) {
                mapper.deleteAvatar(user.getAvatar());
            }
            String newAvatarUrl = mapper.processAvatar(dto.getAvatar(), user.getUsername());
            user.setAvatar(newAvatarUrl);
        }

        userRepository.save(user);

        return ResponseEntity.ok("User updated");
    }
}
