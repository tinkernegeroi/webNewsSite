package com.javaLab.web.controllers;

import com.javaLab.web.dto.ServerTimeDTO;
import com.javaLab.web.dto.UserEditDTO;
import com.javaLab.web.dto.UserResponseDTO;
import com.javaLab.web.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST-контроллер для операций с профилем авторизованного пользователя.
 * Работает через HTTP сессии.
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Получить профиль текущего пользователя.
     *
     * @param session HTTP сессия
     * @return ResponseEntity с данными профиля
     */
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> profile(HttpSession session) {
        return userService.getProfile(session);
    }

    /**
     * Редактировать профиль пользователя (multipart/form-data).
     *
     * @param session HTTP сессия
     * @param dto данные для обновления
     * @return ResponseEntity с результатом
     */
    @PatchMapping(value = "/edit", consumes = "multipart/form-data")
    public ResponseEntity<String> editUser(
            HttpSession session,
            @ModelAttribute UserEditDTO dto
    ) {
        return userService.editUser(session, dto);
    }

    /**
     * Получить текущее серверное время.
     *
     * @return ResponseEntity с ServerTimeDTO
     */
    @GetMapping("/time")
    public ResponseEntity<ServerTimeDTO> time(){
        return ResponseEntity.ok(userService.getServerTime());
    }

    /**
     * Загрузить аватар пользователя.
     *
     * @param session HTTP сессия
     * @param file файл аватара
     * @return ResponseEntity с результатом загрузки
     */
    @PostMapping("/avatar")
    public ResponseEntity<?> uploadAvatar(HttpSession session,
                                          @RequestParam MultipartFile file) {
        return userService.uploadAvatar(session, file);
    }

    /**
     * Удалить аватар пользователя.
     *
     * @param session HTTP сессия
     * @return ResponseEntity с результатом удаления
     */
    @DeleteMapping("/avatar")
    public ResponseEntity<?> deleteAvatar(HttpSession session) {
        return userService.deleteAvatar(session);
    }
}
