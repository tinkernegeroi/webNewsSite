package com.javaLab.web.services;

import com.javaLab.web.Mapper;
import com.javaLab.web.dto.UserCreateDTO;
import com.javaLab.web.models.UserModel;
import com.javaLab.web.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public ResponseEntity<?> registerUser(UserCreateDTO userCreateDTO) {
        Optional<String> error = checkIfUserExists(userCreateDTO);
        if (error.isPresent()) {
            return new ResponseEntity<>(error.get(), HttpStatus.CONFLICT);
        }
        error = validateUserInput(userCreateDTO);
        if (error.isPresent()) {
            return new ResponseEntity<>(error.get(), HttpStatus.BAD_REQUEST);
        }
        UserModel savedUser = userRepository.save(Mapper.userCreateDtoToUser(userCreateDTO));
        return ResponseEntity.ok(Mapper.userToUserResponseDto(savedUser));
    }

    private Optional<String> checkIfUserExists(UserCreateDTO userCreateDTO) {
        if (userRepository.findByUsername(userCreateDTO.getUsername()).isPresent()) {
            return Optional.of("Пользователь с таким логином уже зарегистрирован");
        }
        if (userRepository.findByEmail(userCreateDTO.getEmail()).isPresent()) {
            return Optional.of("Пользователь с такой почтой уже зарегистрирован");
        }
        return Optional.empty();
    }
}
