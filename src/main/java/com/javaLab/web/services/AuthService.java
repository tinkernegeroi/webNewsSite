package com.javaLab.web.services;

import com.javaLab.web.dto.UserCreateDTO;
import com.javaLab.web.dto.UserLoginDTO;
import com.javaLab.web.dto.UserResponseDTO;
import com.javaLab.web.models.User;
import com.javaLab.web.repository.UserRepository;
import com.javaLab.web.utils.Mapper;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final Mapper mapper;

    public ResponseEntity<?> register(UserCreateDTO dto) {

        Optional<String> error = checkIfUserExists(dto);
        if (error.isPresent()) return ResponseEntity.badRequest().body(error.get());

        User savedUser = userRepository.save(mapper.userCreateSchemaToDTO(dto));
        return ResponseEntity.ok(mapper.userToUserResponseDTO(savedUser));
    }

    public ResponseEntity<?> login(UserLoginDTO userLoginDTO, HttpSession session) {
        Optional<User> userOptional = userRepository.findByUsername(userLoginDTO.getUsername());
        if(userOptional.isEmpty()){
            return new ResponseEntity<>
                    ("Пользователь с таким логином не зарегистрирован", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();

        if (!user.getPassword().equals(userLoginDTO.getPassword())) {
            return new ResponseEntity<>("Неправильный пароль", HttpStatus.UNAUTHORIZED);
        }
        UserResponseDTO userResponseDTO = mapper.userToUserResponseDTO(user);
        session.setAttribute("user", userResponseDTO.getUsername());
        System.out.println(session.getAttribute("user"));
        return ResponseEntity.ok(userResponseDTO);
    }

    public ResponseEntity<?> logout(HttpSession session){
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
