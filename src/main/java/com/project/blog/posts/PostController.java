package com.project.blog.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> getBlogPosts(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getBlogPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getBlogPost(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getBlogPost(id));
    }

    @PostMapping
    public ResponseEntity<Post> newBlogPost(@RequestBody PostDTO post){
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.newBlogPost(post));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Post> updateBlogPost(@PathVariable("id") Long id, @RequestBody PostDTO post){
        Post updatedPost = postService.updateBlogPost(id, post);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlogPost(@PathVariable("id") Long id){
        postService.deleteBlogPost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
