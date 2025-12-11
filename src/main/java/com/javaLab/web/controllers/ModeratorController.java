package com.javaLab.web.controllers;


import com.javaLab.web.dto.NewsCreateDTO;
import com.javaLab.web.services.ModeratorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
@AllArgsConstructor
public class ModeratorController {
    private final ModeratorService moderatorService;

    @PostMapping
    public ResponseEntity<?> createNews (@ModelAttribute NewsCreateDTO dto) {
        return moderatorService.createNews(dto);
    }

    @GetMapping
    public ResponseEntity<?> getNews() {
        return moderatorService.getAllNews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable Long id) {
        return moderatorService.getNewsById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> editNews(@PathVariable Long id, @ModelAttribute NewsCreateDTO dto) {
        return moderatorService.editNews(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable Long id) {
        return moderatorService.deleteNewsById(id);
    }
}
