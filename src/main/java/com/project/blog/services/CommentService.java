package com.project.blog.services;

import com.project.blog.domain.Comment;
import com.project.blog.dtos.CommentDTO;

import java.util.List;

public interface CommentService {
    public Comment newComment(CommentDTO commentDTO);
    public List<Comment> getCommentsByPostId(Long postId);
    public Comment getComment(Long id);
    public Comment updateComment(Long id, CommentDTO comment);
    public void deleteComment(Long id);
}
