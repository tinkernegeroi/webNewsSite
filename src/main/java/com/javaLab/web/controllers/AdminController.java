package com.javaLab.web.controllers;

import com.javaLab.web.dto.AdminCreateUserDTO;
import com.javaLab.web.dto.AdminEditUserDTO;
import com.javaLab.web.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * REST-контроллер для административных операций с пользователями.
 * Доступ только для роли ADMIN.
 */
@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;

    /**
     * Получить список всех пользователей.
     *
     * @return ResponseEntity с данными пользователей
     */
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return adminService.getAllUsers();
    }

    /**
     * Получить пользователя по ID.
     *
     * @param id идентификатор пользователя
     * @return ResponseEntity с данными пользователя
     */
    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return adminService.getUserById(id);
    }

    /**
     * Создать нового пользователя (multipart/form-data).
     *
     * @param dto данные для создания пользователя
     * @return ResponseEntity с результатом операции
     */
    @PostMapping(value = "/create", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createUser(@ModelAttribute AdminCreateUserDTO dto) {
        return adminService.createUser(dto);
    }

    /**
     * Обновить пользователя по ID (multipart/form-data).
     *
     * @param id идентификатор пользователя
     * @param dto данные для обновления
     * @return ResponseEntity с результатом операции
     */
    @PatchMapping(value = "/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateUserAsAdmin(
            @PathVariable Long id,
            @ModelAttribute AdminEditUserDTO dto
    ) {
        return adminService.updateUser(id, dto);
    }

    /**
     * Удалить пользователя по ID.
     *
     * @param id идентификатор пользователя
     * @return ResponseEntity с результатом операции
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return adminService.deleteUserById(id);
    }
}
