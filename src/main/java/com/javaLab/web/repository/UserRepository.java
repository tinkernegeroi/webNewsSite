package com.javaLab.web.repository;

import com.javaLab.web.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA репозиторий для сущности User.
 * Предоставляет CRUD операции и поиск по username/email.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по уникальному имени.
     *
     * @param username имя пользователя
     * @return Optional с пользователем или пустой
     */
    Optional<User> findByUsername(String username);

    /**
     * Находит пользователя по уникальному email.
     *
     * @param email email пользователя
     * @return Optional с пользователем или пустой
     */
    Optional<User> findByEmail(String email);
}
