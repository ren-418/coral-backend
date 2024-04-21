package com.coral.backend.controllers;

import com.coral.backend.dtos.RegisterDTO;
import com.coral.backend.dtos.SearchDTO;
import com.coral.backend.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/searchAsEnterprise")
    public ResponseEntity<Object> searchAsEnterprise(@RequestBody SearchDTO searchDTO) {
        return searchService.searchAsEnterprise(searchDTO);
    }
}
