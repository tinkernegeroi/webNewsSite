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

/**
 * Утилитный класс для маппинга DTO ↔ Entity и обработки файлов.
 * Содержит логику валидации email и работы с аватарами/изображениями.
 */
@Component
@AllArgsConstructor
@Slf4j
public class Mapper {

    private final ImageConfig config;

    private static final Role DEFAULT_ROLE = Role.VISITOR;
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Преобразует UserCreateDTO в User entity.
     * Устанавливает роль VISITOR по умолчанию.
     *
     * @param dto данные для создания пользователя
     * @return User entity
     */
    public User userCreateSchemaToDTO(UserCreateDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(DEFAULT_ROLE);
        user.setAvatar(processAvatar(dto.getAvatar(), dto.getUsername()));
        return user;
    }

    /**
     * Обрабатывает загрузку файла аватара/изображения.
     * Сохраняет файл и возвращает URL.
     *
     * @param avatar файл изображения
     * @param username имя пользователя или заголовок для имени файла
     * @return URL изображения или путь по умолчанию
     * @throws FileUploadException при ошибках сохранения файла
     */
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

    /**
     * Удаляет файл изображения с диска.
     * Пропускает изображения по умолчанию.
     *
     * @param imageUrl URL изображения
     */
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

    /**
     * Преобразует User entity в UserResponseDTO.
     *
     * @param user пользователь
     * @return UserResponseDTO
     */
    public UserResponseDTO userToUserResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAvatar(),
                user.getRole(),
                user.getVisitsCount()
        );
    }

    /**
     * Преобразует NewsCreateDTO в News entity.
     *
     * @param dto данные новости
     * @param newsTitle заголовок для имени файла
     * @return News entity
     */
    public News newsCreateDTOToNews(NewsCreateDTO dto, String newsTitle) {
        News news = new News();
        news.setTitle(dto.getTitle());
        news.setDescription(dto.getDescription());
        news.setImageSrc(processAvatar(dto.getImage(), newsTitle));
        return news;
    }

    /**
     * Преобразует News entity в NewsResponseDTO.
     *
     * @param news новость
     * @return NewsResponseDTO
     */
    public NewsResponseDTO newsToNewsResponseDTO(News news) {
        return new NewsResponseDTO(
                news.getId(),
                news.getTitle(),
                news.getDescription(),
                news.getImageSrc()
        );
    }

    /**
     * Возвращает базовый URL для изображений.
     *
     * @return базовый URL изображений
     */
    public String getDefaultImageUrl() {
        return config.getImageUrl();
    }

    /**
     * Валидирует email по регулярному выражению.
     *
     * @param email строка email
     * @return true если email валидный
     */
    public boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
