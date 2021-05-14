package com.project.blog.controllers;

import com.project.blog.dtos.CommentDTO;
import com.project.blog.services.CommentService;
import com.project.blog.domain.Comment;
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

    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsByPost(@RequestParam("postId") Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByPostId(postId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComment(id));
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<Comment> updateComment(@PathVariable("id") Long id, @RequestBody CommentDTO commentDTO){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(id, commentDTO));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id){
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
