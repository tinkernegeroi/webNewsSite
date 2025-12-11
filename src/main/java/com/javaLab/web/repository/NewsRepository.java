package com.javaLab.web.repository;

import com.javaLab.web.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long>{
    Optional<News> findByTitle(String title);
}