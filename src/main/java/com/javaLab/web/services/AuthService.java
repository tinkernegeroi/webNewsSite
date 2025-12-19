package com.javaLab.web.services;

import com.javaLab.web.dto.UserCreateDTO;
import com.javaLab.web.dto.UserLoginDTO;
import com.javaLab.web.dto.UserResponseDTO;
import com.javaLab.web.exceptions.UserNotFoundException;
import com.javaLab.web.models.User;
import com.javaLab.web.repository.UserRepository;
import com.javaLab.web.utils.Mapper;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final Mapper mapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> register(UserCreateDTO dto) {
        Optional<String> error = checkIfUserExists(dto);
        if (error.isPresent()) return ResponseEntity.badRequest().body(error.get());
        if (mapper.isValidEmail(dto.getEmail())) {
            User user = mapper.userCreateSchemaToDTO(dto);
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            User savedUser = userRepository.save(user);
            return ResponseEntity.ok(mapper.userToUserResponseDTO(savedUser));
        }
        else return ResponseEntity.badRequest().body(error.get());

    }

    public ResponseEntity<?> login(UserLoginDTO userLoginDTO, HttpSession session) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDTO.getUsername(),
                            userLoginDTO.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            User user = userRepository.findByUsername(userLoginDTO.getUsername())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            user.setVisitsCount(user.getVisitsCount() + 1);
            userRepository.save(user);

            return ResponseEntity.ok(mapper.userToUserResponseDTO(user));
        } catch (Exception e) {
            return new ResponseEntity<>("Неверные данные", HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<?> logout(HttpSession session) {
        SecurityContextHolder.clearContext();
        if (session != null) session.invalidate();
        return ResponseEntity.ok("Logged out");
    }

    private Optional<String> checkIfUserExists(UserCreateDTO userCreateDTO) {
        if (userRepository.findByUsername(userCreateDTO.getUsername()).isPresent())
            return Optional.of("Пользователь с таким логином уже зарегистрирован");
        if (userRepository.findByEmail(userCreateDTO.getEmail()).isPresent())
            return Optional.of("Пользователь с такой почтой уже зарегистрирован");
        return Optional.empty();
    }
}
