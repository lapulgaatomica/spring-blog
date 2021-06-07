package com.project.blog.services;

import com.project.blog.entities.Comment;
import com.project.blog.payloads.CommentDTO;

public interface CommentService {
    Comment newComment(Long postId, CommentDTO commentDTO);
    Comment getComment(Long id);
    Comment updateComment(Long id, CommentDTO comment);
    void deleteComment(Long id);
}
