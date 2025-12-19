package com.javaLab.web.repository;

import com.javaLab.web.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA репозиторий для сущности News.
 * Предоставляет CRUD операции и поиск по заголовку.
 */
public interface NewsRepository extends JpaRepository<News, Long> {

    /**
     * Находит новость по уникальному заголовку.
     *
     * @param title заголовок новости
     * @return Optional с новостью или пустой
     */
    Optional<News> findByTitle(String title);
}
