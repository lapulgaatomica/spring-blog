package com.project.blog.controllers;

import com.project.blog.entities.Post;
import com.project.blog.payloads.PostRequest;
import com.project.blog.payloads.PostWithCommentsResponse;
import com.project.blog.services.PostService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
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
    public ResponseEntity<PostWithCommentsResponse> getBlogPost(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getBlogPostWithComment(id));
    }

    @PostMapping
    public ResponseEntity<Post> newBlogPost(@ApiParam(hidden = true) @AuthenticationPrincipal String user,
                                            @RequestBody @Valid PostRequest post){
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.newBlogPost(post, user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Post> updateBlogPost(@ApiParam(hidden = true) @AuthenticationPrincipal String user,
                                               @PathVariable("id") Long id,
                                               @RequestBody PostRequest post){
        Post updatedPost = postService.updateBlogPost(id, post, user);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasAuthority('post:write')")
    public ResponseEntity<?> deleteBlogPost(@ApiParam(hidden = true) Authentication authentication,
                                            @PathVariable("id") Long id){
        postService.deleteBlogPost(id, authentication.getName(), authentication.getAuthorities());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
