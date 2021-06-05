package com.project.blog.services;

import com.project.blog.entities.BlogUser;
import com.project.blog.entities.Comment;
import com.project.blog.entities.Post;
import com.project.blog.dtos.CommentDTO;
import com.project.blog.repositories.BlogUserRepository;
import com.project.blog.repositories.CommentRepository;
import com.project.blog.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final BlogUserRepository userRepository;

    @Override
    public Comment newComment(Long postId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId).get();
        BlogUser currentLoggedInUser = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
        ).get();
        return commentRepository.save(new Comment(
                null, commentDTO.getContent(), LocalDateTime.now(), null, post, currentLoggedInUser));
    }

    @Override
    public Comment getComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "Comment with ID " + id + " does not exist"));
        return comment;
    }

    @Transactional
    @Override
    public Comment updateComment(Long id, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "Comment with ID " + id + " does not exist"));
        comment.setContent(commentDTO.getContent());
        comment.setDateEdited(LocalDateTime.now());
        return comment;
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
