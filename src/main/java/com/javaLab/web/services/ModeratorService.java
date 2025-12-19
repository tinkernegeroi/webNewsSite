package com.javaLab.web.services;

import com.javaLab.web.dto.NewsCreateDTO;
import com.javaLab.web.exceptions.NewsNotFoundException;
import com.javaLab.web.models.News;
import com.javaLab.web.repository.NewsRepository;
import com.javaLab.web.utils.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис управления новостями для модераторов и администраторов.
 */
@Service
@AllArgsConstructor
public class ModeratorService {
    private final NewsRepository newsRepository;
    private final Mapper mapper;

    /**
     * Создает новую новость с обработкой изображения.
     *
     * @param newsCreateDTO данные новости
     * @return ResponseEntity с NewsResponseDTO
     */
    public ResponseEntity<?> createNews(NewsCreateDTO newsCreateDTO) {
        String title = newsCreateDTO.getTitle();
        News news = mapper.newsCreateDTOToNews(newsCreateDTO, title);
        news = newsRepository.save(news);
        return ResponseEntity.ok(mapper.newsToNewsResponseDTO(news));
    }

    /**
     * Возвращает все новости (публичный доступ).
     *
     * @return ResponseEntity со списком новостей
     */
    public ResponseEntity<?> getAllNews() {
        List<News> news = newsRepository.findAll();
        return ResponseEntity.ok(news);
    }

    /**
     * Получает новость по ID.
     *
     * @param id идентификатор новости
     * @return ResponseEntity с данными новости
     */
    public ResponseEntity<?> getNewsById(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException("News not found"));
        return ResponseEntity.ok(news);
    }

    /**
     * Обновляет существующую новость.
     * Обновляет только переданные поля.
     *
     * @param id идентификатор новости
     * @param newsCreateDTO новые данные
     * @return ResponseEntity с обновленной новостью
     */
    public ResponseEntity<?> editNews(Long id, NewsCreateDTO newsCreateDTO) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException("News not found"));

        if (newsCreateDTO.getTitle() != null && !newsCreateDTO.getTitle().isBlank()) {
            news.setTitle(newsCreateDTO.getTitle());
        }

        if (newsCreateDTO.getDescription() != null && !newsCreateDTO.getDescription().isBlank()) {
            news.setDescription(newsCreateDTO.getDescription());
        }

        if (newsCreateDTO.getImage() != null && !newsCreateDTO.getImage().isEmpty()) {
            if (!news.getImageSrc().isEmpty()) {
                mapper.deleteAvatar(news.getImageSrc());
            }
            String imageSrc = mapper.processAvatar(newsCreateDTO.getImage(), newsCreateDTO.getTitle());
            news.setImageSrc(imageSrc);
        }

        news = newsRepository.save(news);
        return ResponseEntity.ok(news);
    }

    /**
     * Удаляет новость и её изображение.
     *
     * @param id идентификатор новости
     * @return ResponseEntity с подтверждением удаления
     */
    public ResponseEntity<?> deleteNewsById(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException("News not found"));
        mapper.deleteAvatar(news.getImageSrc());
        newsRepository.delete(news);
        return ResponseEntity.ok("News has been deleted");
    }
}
