package com.project.blog.controllers;

import com.project.blog.payloads.CommentRequest;
import com.project.blog.services.CommentService;
import com.project.blog.entities.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> newComment(@PathVariable("postId") Long postId,
                                              @RequestBody CommentRequest comment,
                                              @AuthenticationPrincipal String user){
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.newComment(postId, comment, user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComment(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable("id") Long id,
                                                 @RequestBody CommentRequest commentRequest,
                                                 @AuthenticationPrincipal String user){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(id, commentRequest, user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasAuthority('comment:write')")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id,
                                           Authentication authentication){
        commentService.deleteComment(id, authentication);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
