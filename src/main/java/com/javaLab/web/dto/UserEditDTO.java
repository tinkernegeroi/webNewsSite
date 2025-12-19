package com.javaLab.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * DTO для редактирования профиля пользователя.
 * Используется в UserController.editUser().
 */
@Data
@AllArgsConstructor
public class UserEditDTO {

    /**
     * Новое имя пользователя.
     */
    private String username;

    /**
     * Новый пароль (опционально, если не меняется).
     */
    private String password;

    /**
     * Новый email.
     */
    private String email;

    /**
     * Новый аватар (опционально, multipart/form-data).
     */
    private MultipartFile avatar;
}
