package com.javaLab.web.utils;

import com.javaLab.web.configs.ImageConfig;
import com.javaLab.web.dto.NewsCreateDTO;
import com.javaLab.web.dto.NewsResponseDTO;
import com.javaLab.web.dto.UserCreateDTO;
import com.javaLab.web.dto.UserResponseDTO;
import com.javaLab.web.models.News;
import com.javaLab.web.models.Role;
import com.javaLab.web.models.User;
import com.javaLab.web.exceptions.FileUploadException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
@Slf4j
public class Mapper {

    private final ImageConfig config;

    private static final Role DEFAULT_ROLE = Role.VISITOR;

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public User userCreateSchemaToDTO(UserCreateDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(DEFAULT_ROLE);
        user.setAvatar(processAvatar(dto.getAvatar(), dto.getUsername()));
        return user;
    }

    public String processAvatar(MultipartFile avatar, String username) {
        if (avatar != null && !avatar.isEmpty()) {
            try {
                String filename = username + "_" + avatar.getOriginalFilename();
                File file = new File(config.getImagePath(), filename);
                avatar.transferTo(file);

                return config.getImageUrl() + filename;

            } catch (IOException e) {
                throw new FileUploadException("Не удалось загрузить аватар");
            }
        }
        return config.getDefaultImage();
    }

    public void deleteAvatar(String imageUrl) {

        if (imageUrl == null || imageUrl.equals(config.getDefaultImage())) {
            return;
        }

        try {

            String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            Path path = Paths.get(config.getImagePath(), filename);

            if (Files.exists(path)) {
                Files.delete(path);
                log.info("Image deleted: {}", path);
            } else {
                log.warn("Image file not found, cannot delete: {}", path);
            }
        } catch (IOException e) {
            log.error("Failed to delete Image: {}", imageUrl, e);
        }
    }

    public UserResponseDTO userToUserResponseDTO(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAvatar(),
                user.getRole(),
                user.getVisitsCount()
        );
    }

    public News newsCreateDTOToNews(NewsCreateDTO dto, String newsTitle) {
        News news = new News();
        news.setTitle(dto.getTitle());
        news.setDescription(dto.getDescription());
        news.setImageSrc(processAvatar(dto.getImage(), newsTitle));
        return news;
    }

    public NewsResponseDTO newsToNewsResponseDTO(News news){
        return new NewsResponseDTO(
                news.getId(),
                news.getTitle(),
                news.getDescription(),
                news.getImageSrc()
        );
    }

    public String getDefaultImageUrl(){
        return config.getImageUrl();
    }

    public boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
}

