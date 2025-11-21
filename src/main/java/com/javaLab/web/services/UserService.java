package com.javaLab.web.services;

import com.javaLab.web.dto.ServerTimeDTO;
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
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setVisitsCount(user.getVisitsCount() + 1);
        userRepository.save(user);
        return ResponseEntity.ok(Mapper.userToUserResponseDTO(user));
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

        String avatarUrl = Mapper.processAvatar(file, username);
        user.setAvatar(avatarUrl);
        userRepository.save(user);

        return ResponseEntity.ok("Avatar uploaded");
    }

    public ResponseEntity<String> deleteAvatar(HttpSession session) {
        String username = getUsernameFromSession(session);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setAvatar(Mapper.DEFAULT_IMAGE_URL);
        userRepository.save(user);

        return ResponseEntity.ok("Avatar deleted");
    }
}
