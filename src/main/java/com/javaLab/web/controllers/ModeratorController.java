package com.javaLab.web.controllers;

import com.javaLab.web.dto.NewsCreateDTO;
import com.javaLab.web.services.ModeratorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST-контроллер для управления новостями.
 * Доступ: MODERATOR и ADMIN (кроме getallnews - публичный).
 */
@RestController
@RequestMapping("/news")
@AllArgsConstructor
public class ModeratorController {
    private final ModeratorService moderatorService;

    /**
     * Создать новую новость (multipart/form-data).
     *
     * @param dto данные новости
     * @return ResponseEntity с результатом создания
     */
    @PostMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createNews (@ModelAttribute NewsCreateDTO dto) {
        return moderatorService.createNews(dto);
    }

    /**
     * Получить все новости (публичный доступ).
     *
     * @return ResponseEntity со списком новостей
     */
    @GetMapping(value="/getallnews")
    public ResponseEntity<?> getNews() {
        return moderatorService.getAllNews();
    }

    /**
     * Получить новость по ID.
     *
     * @param id идентификатор новости
     * @return ResponseEntity с данными новости
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getNewsById(@PathVariable Long id) {
        return moderatorService.getNewsById(id);
    }

    /**
     * Редактировать новость (multipart/form-data).
     *
     * @param id идентификатор новости
     * @param dto обновленные данные
     * @return ResponseEntity с результатом редактирования
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> editNews(@PathVariable Long id, @ModelAttribute NewsCreateDTO dto) {
        return moderatorService.editNews(id, dto);
    }

    /**
     * Удалить новость по ID.
     *
     * @param id идентификатор новости
     * @return ResponseEntity с результатом удаления
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteNews(@PathVariable Long id) {
        return moderatorService.deleteNewsById(id);
    }
}
