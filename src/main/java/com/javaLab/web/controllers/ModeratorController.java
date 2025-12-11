package com.javaLab.web.controllers;


import com.javaLab.web.dto.NewsCreateDTO;
import com.javaLab.web.services.ModeratorService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moderator")
@AllArgsConstructor
public class ModeratorController {
    private final ModeratorService moderatorService;

    @PostMapping("/create")
    public ResponseEntity<?> createNews (@ModelAttribute NewsCreateDTO dto) {
        return moderatorService.createNews(dto);
    }

    @GetMapping("/news")
    public ResponseEntity<?> getNews(HttpSession session) {
        return moderatorService.getAllNews();
    }

}
