package com.javaLab.web.utils;

import com.javaLab.web.dto.UserCreateDTO;
import com.javaLab.web.dto.UserResponseDTO;
import com.javaLab.web.models.Role;
import com.javaLab.web.models.User;
import com.javaLab.web.exceptions.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class Mapper {
    private static final Role DEFAULT_ROLE = Role.VISITOR;

    private static final String IMAGE_PATH = "C:\\Users\\Roman\\IdeaProjects\\web" +
            "\\src\\main\\resources\\static\\images";

    private final static String IMAGE_URL = "http://localhost:8080/images/";

    public final static String DEFAULT_IMAGE_URL = "http://localhost:8080/images/DEFAULT.png";

    public static User userCreateSchemaToDTO(UserCreateDTO userCreateDTO){
        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(userCreateDTO.getPassword());
        user.setEmail(userCreateDTO.getEmail());
        user.setRole(DEFAULT_ROLE);
        user.setAvatar(processAvatar(userCreateDTO.getAvatar(), userCreateDTO.getUsername()));
        return user;
    }

    public static String processAvatar(MultipartFile avatar, String username){
        if (avatar != null) {
            try {
                String Filename = username + "_" + avatar.getOriginalFilename();
                File file = new File(IMAGE_PATH, Filename);
                avatar.transferTo(file);
                return IMAGE_URL + Filename;
            } catch (IOException e) {
                throw new FileUploadException("Не удалось загрузить аватар");
            }
        }else {
            return DEFAULT_IMAGE_URL;
        }
    }

    public static UserResponseDTO userToUserResponseDTO(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAvatar(),
                user.getRole(),
                user.getVisitsCount()
        );
    }
}
