package com.project.blog.comments;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> newComment(@RequestBody CommentDTO comment){
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.newComment(comment));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable("postId") Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByPostId(postId));
    }
}
