package com.coral.backend.services;

import com.coral.backend.dtos.*;
import com.coral.backend.entities.*;
import com.coral.backend.repositories.EnterpriseUserRepository;
import com.coral.backend.repositories.InvestorUserRepository;
import com.coral.backend.repositories.PostRepository;
import com.coral.backend.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NewsService {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private EnterpriseUserRepository enterpriseUserRepository;
    @Autowired
    private InvestorUserRepository investorUserRepository;

    public ResponseEntity<Object> createPost(NewsCreationDTO requestBody) {
        Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSessionToken());
        if (optionalSession.isEmpty()) {
            return new ResponseEntity<>("Session expired", HttpStatus.NOT_FOUND);
        }

        User creator = optionalSession.get().getUser();
        EnterpriseUser enterprise = enterpriseUserRepository.findEnterpriseUserByUserId(creator.getUserId());
        List<Post> posts = enterprise.getPosts();

        Post newPost = new Post();
        newPost.setTitle(requestBody.getTitle());
        newPost.setDescription(requestBody.getDescription());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        newPost.setCreatedAt(timestamp);
        newPost.setImage(encodeImage(requestBody.getImage()));
        newPost.setEnterpriseUser(enterprise);

        posts.add(newPost);
        enterprise.setPosts(posts);

        postRepository.save(newPost);
        enterpriseUserRepository.save(enterprise);

        return new ResponseEntity<>("Post created successfully", HttpStatus.OK);
    }

    public ResponseEntity<Object> getNews(CheckSessionDTO requestBody) {
        Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSessionToken());
        if (optionalSession.isEmpty()) {
            return new ResponseEntity<>("Session expired", HttpStatus.NOT_FOUND);
        }
        User client = optionalSession.get().getUser();
        InvestorUser investorUser = investorUserRepository.findInvestorUserByUserId(client.getUserId());

        List<EnterpriseUser> investmetsList = investorUser.getEnterprises();
        List<PostDTO> postDTOList = new ArrayList<>();
        if (!investmetsList.isEmpty()) {
            for (EnterpriseUser enterpriseUser : investmetsList) {
                List<Post> posts = enterpriseUser.getPosts();
                if (!posts.isEmpty()) {
                    for (Post post : posts) {
                        postDTOList.add(toPostDto(post));
                    }
                }
            }
        }


        NewsListDTO newsListDTO = new NewsListDTO();
        newsListDTO.setPosts(postDTOList);
        return new ResponseEntity<>(newsListDTO, HttpStatus.OK);
    }

    public byte[] encodeImage(String base64){
        String encodedString = Base64.getEncoder().encodeToString(base64.getBytes());
        return java.util.Base64.getDecoder().decode(encodedString);
    }

    public ResponseEntity<Object> modifyPost(NewsModificationDTO requestBody) {
        Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSessionToken());
        if (optionalSession.isEmpty()) {
            return new ResponseEntity<>("Session expired", HttpStatus.BAD_REQUEST);
        }
        User client = optionalSession.get().getUser();
        EnterpriseUser enterpriseUser = enterpriseUserRepository.findEnterpriseUserByUserId(client.getUserId());

        Post post = postRepository.findByIdAndEnterpriseUser(requestBody.getPostId(), enterpriseUser);

        if (post != null) {
            if (enterpriseUser.getUserId() != post.getEnterpriseUser().getUserId()) {
                return new ResponseEntity<>("You do not own this post", HttpStatus.FORBIDDEN);
            }
            post.setDescription(requestBody.getDescription());
            post.setImage(encodeImage(requestBody.getImage()));
            post.setTitle(requestBody.getTitle());
            postRepository.save(post);
            return new ResponseEntity<>("Post modified succesfully" ,HttpStatus.OK);
        }
        return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<Object> deletePost(DeletePostDTO requestBody) {
        Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSessionToken());
        if (optionalSession.isEmpty()) {
            return new ResponseEntity<>("Session expired", HttpStatus.BAD_REQUEST);
        }
        User client = optionalSession.get().getUser();
        EnterpriseUser enterpriseUser = enterpriseUserRepository.findEnterpriseUserByUserId(client.getUserId());


        Post post = postRepository.findByIdAndEnterpriseUser(requestBody.getPostId(), enterpriseUser);
        if (post != null) {
            if (enterpriseUser.getUserId() != post.getEnterpriseUser().getUserId()) {
                return new ResponseEntity<>("You do not own this post", HttpStatus.FORBIDDEN);
            }

            List<Post> posts = enterpriseUser.getPosts();
            posts.remove(post);
            postRepository.delete(post);
            return new ResponseEntity<>("Post deleted successfully" ,HttpStatus.OK);
        }
        return new ResponseEntity<>("Post not found", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> getNewsByArea(CheckSessionDTO requestBody) {
        Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSessionToken());
        if (optionalSession.isEmpty()) {
            return new ResponseEntity<>("Session expired", HttpStatus.BAD_REQUEST);
        }
        User client = optionalSession.get().getUser();
        InvestorUser investorUser = investorUserRepository.findInvestorUserByUserId(client.getUserId());


        List<EnterpriseUser> sameAreasEnterprises = new ArrayList<>();
        for (Area area : investorUser.getAreas()) {
            sameAreasEnterprises.addAll(enterpriseUserRepository.findAllByAreas(area));
        }

        Set<EnterpriseUser> sameAreasEnterprisesSet = new HashSet<>(sameAreasEnterprises);
        List<PostDTO> postDTOList = new ArrayList<>();
        for (EnterpriseUser enterpriseUser : sameAreasEnterprisesSet) {
            List<Post> posts = enterpriseUser.getPosts();
            for (Post post : posts) {
                postDTOList.add(toPostDto(post));
            }
        }

        NewsListDTO newsListDTO = new NewsListDTO();
        newsListDTO.setPosts(postDTOList);
        return new ResponseEntity<>(newsListDTO, HttpStatus.OK);
    }

    public ResponseEntity<Object> getNewsByLocation(CheckSessionDTO requestBody) {
        Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSessionToken());
        if (optionalSession.isEmpty()) {
            return new ResponseEntity<>("Session expired", HttpStatus.BAD_REQUEST);
        }
        User client = optionalSession.get().getUser();
        InvestorUser investorUser = investorUserRepository.findInvestorUserByUserId(client.getUserId());

        List<EnterpriseUser> sameLocationEnterprises = enterpriseUserRepository.findAllByLocation(investorUser.getLocation());

        List<PostDTO> postDTOList = new ArrayList<>();
        for (EnterpriseUser enterpriseUser : sameLocationEnterprises) {
            List<Post> posts = enterpriseUser.getPosts();
            for (Post post : posts) {
                postDTOList.add(toPostDto(post));
            }
        }

        NewsListDTO newsListDTO = new NewsListDTO();
        newsListDTO.setPosts(postDTOList);
        return new ResponseEntity<>(newsListDTO, HttpStatus.OK);
    }

    private PostDTO toPostDto(Post post) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        postDTO.setImage(new String(post.getImage()));
        postDTO.setDate(dateFormat.format(post.getCreatedAt()));
        postDTO.setEnterpriseUserId(post.getEnterpriseUser().getUserId());
        postDTO.setEnterpriseName(post.getEnterpriseUser().getName());
        postDTO.setEnterpriseProfileImage(post.getEnterpriseUser().toDTO().getProfileImage());
        postDTO.setId(post.getId());
        return postDTO;
    }

    public ResponseEntity<Object> getOwnNews(CheckSessionDTO requestBody) {
        Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSessionToken());
        if (optionalSession.isEmpty()) {
            return new ResponseEntity<>("Session expired", HttpStatus.BAD_REQUEST);
        }
        User client = optionalSession.get().getUser();
        EnterpriseUser enterpriseUser = enterpriseUserRepository.findEnterpriseUserByUserId(client.getUserId());

        if (enterpriseUser == null) {
            return new ResponseEntity<>("Investors dont have posts", HttpStatus.FORBIDDEN);
        }

        List<Post> posts = enterpriseUser.getPosts();
        List<PostDTO> postDTOList = new ArrayList<>();

        if (posts.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

        for (Post post : posts) {
            postDTOList.add(toPostDto(post));
        }
        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }
}
