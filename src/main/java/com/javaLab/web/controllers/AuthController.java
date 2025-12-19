package com.javaLab.web.controllers;

import com.javaLab.web.dto.UserCreateDTO;
import com.javaLab.web.dto.UserLoginDTO;
import com.javaLab.web.services.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST-контроллер для аутентификации пользователей.
 * Обеспечивает регистрацию, вход и выход из системы.
 */
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Регистрация нового пользователя.
     *
     * @param username имя пользователя
     * @param password пароль
     * @param email email
     * @param avatar аватар (опционально)
     * @return ResponseEntity с результатом регистрации
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam(required = false) MultipartFile avatar
    ){
        UserCreateDTO dto = new UserCreateDTO(username, password, email, avatar);
        return authService.register(dto);
    }

    /**
     * Вход в систему с созданием сессии.
     *
     * @param userLoginDTO данные для входа
     * @param session HTTP сессия
     * @return ResponseEntity с результатом входа
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody UserLoginDTO userLoginDTO,
            HttpSession session
    ){
        return authService.login(userLoginDTO, session);
    }

    /**
     * Выход из системы с завершением сессии.
     *
     * @param session HTTP сессия
     * @return ResponseEntity с результатом выхода
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session){
        return authService.logout(session);
    }
}
