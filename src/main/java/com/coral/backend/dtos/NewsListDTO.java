package com.coral.backend.dtos;

import com.coral.backend.entities.Post;

import java.util.List;

public class NewsListDTO {
    List<PostDTO> posts;
    public List<PostDTO> getPosts() {
        return posts;
    }
    public void setPosts(List<PostDTO> posts) {
        this.posts = posts;
    }
}
