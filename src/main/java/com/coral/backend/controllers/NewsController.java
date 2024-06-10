package com.coral.backend.controllers;

import com.coral.backend.dtos.CheckSessionDTO;
import com.coral.backend.dtos.DeletePostDTO;
import com.coral.backend.dtos.NewsCreationDTO;
import com.coral.backend.dtos.NewsModificationDTO;
import com.coral.backend.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping("/create-post")
    public ResponseEntity<Object> createPost(@RequestBody NewsCreationDTO requestBody) {
        return newsService.createPost(requestBody);
    }

    @PostMapping("/get-news")
    public ResponseEntity<Object> getNews(@RequestBody CheckSessionDTO requestBody) {
        return newsService.getNews(requestBody);
    }

    @PostMapping("/edit-post")
    public ResponseEntity<Object> editPost(@RequestBody NewsModificationDTO requestBody) {
        return newsService.modifyPost(requestBody);
    }

    @PostMapping("/delete-post")
    public ResponseEntity<Object> deletePost(@RequestBody DeletePostDTO requestBody) {
        return newsService.deletePost(requestBody);
    }

    @PostMapping("/get-news-areas")
    public ResponseEntity<Object> getNewsArea(@RequestBody CheckSessionDTO requestBody) {
        return newsService.getNewsByArea(requestBody);
    }

    @PostMapping("/get-news-location")
    public ResponseEntity<Object> getNewsLocation(@RequestBody CheckSessionDTO requestBody) {
        return newsService.getNewsByLocation(requestBody);
    }
}
