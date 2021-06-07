package com.project.blog.services;

import com.project.blog.entities.BlogUser;
import com.project.blog.entities.Comment;
import com.project.blog.entities.Post;
import com.project.blog.payloads.CommentDTO;
import com.project.blog.repositories.BlogUserRepository;
import com.project.blog.repositories.CommentRepository;
import com.project.blog.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalStateException(
                "post with ID " + postId + " does not exist"));
        BlogUser currentLoggedInUser = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
        ).orElseThrow(() -> new IllegalStateException("User does not exist"));
        return commentRepository.save(new Comment(
                null, commentDTO.getContent(), LocalDateTime.now(), null, post, currentLoggedInUser));
    }

    @Override
    public Comment getComment(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Comment with ID " + id + " does not exist"));
    }

    @Transactional
    @Override
    public Comment updateComment(Long id, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "Comment with ID " + id + " does not exist"));
        Authentication currentlyLoggedInUser = SecurityContextHolder.getContext().getAuthentication();
        if(currentlyLoggedInUser.getName().equals(comment.getCreator().getUsername())){
            comment.setContent(commentDTO.getContent());
            comment.setDateEdited(LocalDateTime.now());
            return comment;
        }
        throw new IllegalStateException("Sorry you can't edit this comment");
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "Comment with ID " + id + " does not exist"));
        Authentication currentlyLoggedInUser = SecurityContextHolder.getContext().getAuthentication();

        if(currentlyLoggedInUser.getAuthorities().contains(new SimpleGrantedAuthority("comment:write")) ||
                currentlyLoggedInUser.getName().equals(comment.getCreator().getUsername())){
            commentRepository.delete(comment);
        }else{
            throw new IllegalStateException("Sorry you can't delete this comment");
        }
    }
}
