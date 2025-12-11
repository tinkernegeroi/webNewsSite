package com.javaLab.web.services;


import com.javaLab.web.dto.NewsCreateDTO;
import com.javaLab.web.exceptions.NewsNotFoundException;
import com.javaLab.web.exceptions.UserNotFoundException;
import com.javaLab.web.models.News;
import com.javaLab.web.repository.NewsRepository;
import com.javaLab.web.utils.Mapper;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ModeratorService {
    private final NewsRepository newsRepository;

    private final Mapper mapper;

    public ResponseEntity<?> createNews(NewsCreateDTO newsCreateDTO) {
        String title = newsCreateDTO.getTitle();
        News news = mapper.newsCreateDTOToNews(newsCreateDTO, title);
        news = newsRepository.save(news);
        return ResponseEntity.ok(mapper.newsToNewsResponseDTO(news));
    }

    public ResponseEntity<?> getAllNews() {
        List<News> news = newsRepository.findAll();
        return ResponseEntity.ok(news);
    }

    public ResponseEntity<?> getNewsById(Long id){
        News news = newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException("News not found"));
        return ResponseEntity.ok(news);
    }
}
