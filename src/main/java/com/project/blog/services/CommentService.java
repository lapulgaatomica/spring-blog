package com.project.blog.services;

import com.project.blog.entities.Comment;
import com.project.blog.payloads.CommentRequest;

public interface CommentService {
    Comment newComment(Long postId, CommentRequest commentRequest);
    Comment getComment(Long id);
    Comment updateComment(Long id, CommentRequest comment);
    void deleteComment(Long id);
}
