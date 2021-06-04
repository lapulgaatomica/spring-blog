package com.project.blog.services;

import com.project.blog.entities.Comment;
import com.project.blog.dtos.CommentDTO;

import java.util.List;

public interface CommentService {
    Comment newComment(Long postId, CommentDTO commentDTO);
    Comment getComment(Long id);
    Comment updateComment(Long id, CommentDTO comment);
    void deleteComment(Long id);
}
