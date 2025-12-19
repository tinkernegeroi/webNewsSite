package com.javaLab.web.dto;

import com.javaLab.web.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * DTO для создания нового пользователя администратором.
 * Используется в AdminController.createUser().
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminCreateUserDTO {

    /**
     * Имя пользователя (уникальное).
     */
    private String username;

    /**
     * Пароль пользователя (будет зашифрован BCrypt).
     */
    private String password;

    /**
     * Email пользователя (уникальный).
     */
    private String email;

    /**
     * Роль пользователя (ADMIN, MODERATOR, USER).
     */
    private Role role;

    /**
     * Файл аватара (опционально, multipart/form-data).
     */
    private MultipartFile avatar;
}
