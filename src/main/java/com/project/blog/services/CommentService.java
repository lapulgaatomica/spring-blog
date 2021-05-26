package com.project.blog.services;

import com.project.blog.entities.Comment;
import com.project.blog.dtos.CommentDTO;

import java.util.List;

public interface CommentService {
    Comment newComment(CommentDTO commentDTO);
    List<Comment> getCommentsByPostId(Long postId);
    Comment getComment(Long id);
    Comment updateComment(Long id, CommentDTO comment);
    void deleteComment(Long id);
}
