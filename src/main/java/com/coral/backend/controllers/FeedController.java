package com.coral.backend.controllers;

import com.coral.backend.dtos.CheckSessionDTO;
import com.coral.backend.dtos.SearchDTO;
import com.coral.backend.services.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/feed")
public class FeedController {
  @Autowired
  FeedService feedService;

  @PostMapping("/recommended-enterprises")
  public ResponseEntity<Object> getRecommendedEnterprises(@RequestBody CheckSessionDTO checkSessionDTO) {
    return feedService.getRecommendedEnterprises(checkSessionDTO);
  }
}
