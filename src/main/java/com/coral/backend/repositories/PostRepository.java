package com.coral.backend.repositories;

import com.coral.backend.entities.Area;
import com.coral.backend.entities.EnterpriseUser;
import com.coral.backend.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByTitle(String title);
    List<Post> findAllByEnterpriseUser(EnterpriseUser enterpriseUser);
    Post findByIdAndEnterpriseUser(long id, EnterpriseUser enterpriseUser);
}
