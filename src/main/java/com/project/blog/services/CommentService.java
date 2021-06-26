package com.project.blog.services;

import com.project.blog.entities.Comment;
import com.project.blog.payloads.CommentRequest;
import org.springframework.security.core.Authentication;

public interface CommentService {
    Comment newComment(Long postId, CommentRequest commentRequest, String user);
    Comment getComment(Long id);
    Comment updateComment(Long id, CommentRequest comment, String user);
    void deleteComment(Long id, Authentication authentication);
}
