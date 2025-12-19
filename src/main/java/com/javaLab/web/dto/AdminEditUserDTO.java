package com.javaLab.web.dto;

import com.javaLab.web.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * DTO для редактирования пользователя администратором.
 * Используется в AdminController.updateUserAsAdmin().
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminEditUserDTO {

    /**
     * ID пользователя для обновления.
     */
    private Long id;

    /**
     * Новое имя пользователя.
     */
    private String username;

    /**
     * Новый email.
     */
    private String email;

    /**
     * Новый аватар (опционально).
     */
    private MultipartFile avatar;

    /**
     * Новая роль пользователя.
     */
    private Role role;
}
