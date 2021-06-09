package com.project.blog.controllers;

import com.project.blog.payloads.CommentRequest;
import com.project.blog.services.CommentService;
import com.project.blog.entities.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> newComment(@PathVariable("postId") Long postId,
                                              @RequestBody CommentRequest comment){
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.newComment(postId, comment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComment(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable("id") Long id, @RequestBody CommentRequest commentRequest){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(id, commentRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasAuthority('comment:write')")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id){
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
