package com.coral.backend.controllers;

import com.coral.backend.dtos.*;
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
    public ResponseEntity<Object> getSubscribedNews(@RequestBody CheckSessionDTO requestBody) {
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

    @PostMapping("/get-own")
    public ResponseEntity<Object> getOwnNews(@RequestBody CheckSessionDTO requestBody) {
        return newsService.getOwnNews(requestBody);
    }

    @PostMapping("/get-prefixes")
    public ResponseEntity<Object> getPrefixes(@RequestBody PrefixDTO requestBody) {
        return newsService.getPrefixes(requestBody);
    }
}
