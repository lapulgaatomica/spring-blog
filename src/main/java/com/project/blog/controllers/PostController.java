package com.project.blog.controllers;

import com.project.blog.entities.Post;
import com.project.blog.dtos.PostDTO;
import com.project.blog.dtos.PostWithCommentsDTO;
import com.project.blog.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> getBlogPosts(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getBlogPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostWithCommentsDTO> getBlogPost(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getBlogPostWithComment(id));
    }

    @PostMapping
    public ResponseEntity<Post> newBlogPost(@RequestBody @Valid PostDTO post){
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.newBlogPost(post));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Post> updateBlogPost(@PathVariable("id") Long id, @RequestBody PostDTO post){
        Post updatedPost = postService.updateBlogPost(id, post);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasAuthority('post:write')")
    public ResponseEntity<?> deleteBlogPost(@PathVariable("id") Long id){
        postService.deleteBlogPost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
