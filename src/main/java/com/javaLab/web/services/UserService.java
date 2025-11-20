package com.javaLab.web.services;

import com.javaLab.web.Mapper;
import com.javaLab.web.schemas.UserCreateSchema;
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

    public ResponseEntity<?> registerUser(UserCreateSchema userCreateSchema) {
        Optional<String> error = checkIfUserExists(userCreateSchema);
        if (error.isPresent()) {
            return new ResponseEntity<>(error.get(), HttpStatus.CONFLICT);
        }
        error = validateUserInput(userCreateSchema);
        if (error.isPresent()) {
            return new ResponseEntity<>(error.get(), HttpStatus.BAD_REQUEST);
        }
        UserModel savedUser = userRepository.save(Mapper.userCreateSchemaToUser(userCreateSchema));
        return ResponseEntity.ok(Mapper.userToUserResponseSchema(savedUser));
    }

    public ResponseEntity<?> loginUser(UserLoginSchema userLoginSchema){
        
    }

    private Optional<String> checkIfUserExists(UserCreateSchema userCreateSchema) {
        if (userRepository.findByUsername(userCreateSchema.getUsername()).isPresent()) {
            return Optional.of("Пользователь с таким логином уже зарегистрирован");
        }
        if (userRepository.findByEmail(userCreateSchema.getEmail()).isPresent()) {
            return Optional.of("Пользователь с такой почтой уже зарегистрирован");
        }
        return Optional.empty();
    }
}
