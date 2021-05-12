package com.project.blog.comments;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    public Comment newComment(CommentDTO commentDTO);
    public List<Comment> getCommentsByPostId(Long postId);
    public Comment getComment(Long id);
    public Comment updateComment(Long id, CommentDTO comment);
    public void deleteComment(Long id);
}
