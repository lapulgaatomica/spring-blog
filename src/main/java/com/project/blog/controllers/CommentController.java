package com.project.blog.controllers;

import com.project.blog.payloads.CommentRequest;
import com.project.blog.services.CommentService;
import com.project.blog.entities.Comment;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> newComment(@PathVariable("postId") Long postId,
                                              @RequestBody CommentRequest comment,
                                              @ApiParam(hidden = true) @AuthenticationPrincipal String user){
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.newComment(postId, comment, user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComment(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable("id") Long id,
                                                 @RequestBody CommentRequest commentRequest,
                                                 @ApiParam(hidden = true) @AuthenticationPrincipal String user){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(id, commentRequest, user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasAuthority('comment:write')")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id,
                                           @ApiIgnore Authentication authentication){
        commentService.deleteComment(id, authentication.getName(), authentication.getAuthorities());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
