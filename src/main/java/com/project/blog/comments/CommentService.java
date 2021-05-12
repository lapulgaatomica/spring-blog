package com.project.blog.comments;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    public Comment newComment(CommentDTO commentDTO);
    public List<Comment> getCommentsByPostId(Long postId);
    public Optional<Comment> getComment(Long id);
    public Optional<Comment> updateComment(Long id, Comment comment);
    public void deleteComment(Long id);
}
