package com.project.blog.blogposts;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostsController {

    private final PostsService postsService;

    @GetMapping
    public ResponseEntity<List<Posts>> getBlogPosts(){
        return ResponseEntity.status(HttpStatus.OK).body(postsService.getBlogPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Posts>> getBlogPost(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postsService.getBlogPost(id));
    }

    @PostMapping
    public ResponseEntity<Posts> newBlogPost(@RequestBody Posts post){
        Posts newPost = postsService.newBlogPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<Posts>> updateBlogPost(@PathVariable("id") Long id, @RequestBody Posts post){
        Optional<Posts> updatedPost = postsService.updateBlogPost(id, post);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlogPost(@PathVariable("id") Long id){
        postsService.deleteBlogPost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
