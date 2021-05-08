package com.project.blog.blogposts;

import java.util.Optional;

public interface CommentService {
    public Comment newComment(CommentDTO commentDTO);
    public Optional<Comment> getComment(Long id);
    public Optional<Comment> updateComment(Long id, Comment comment);
    public void deleteComment(Long id);
}
