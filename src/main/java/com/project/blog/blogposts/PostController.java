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
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> getBlogPosts(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getBlogPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Post>> getBlogPost(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getBlogPost(id));
    }

    @PostMapping
    public ResponseEntity<Post> newBlogPost(@RequestBody Post post){
        Post newPost = postService.newBlogPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<Post>> updateBlogPost(@PathVariable("id") Long id, @RequestBody Post post){
        Optional<Post> updatedPost = postService.updateBlogPost(id, post);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlogPost(@PathVariable("id") Long id){
        postService.deleteBlogPost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
