package com.project.blog.services;

import com.project.blog.entities.BlogUser;
import com.project.blog.entities.Comment;
import com.project.blog.entities.Post;
import com.project.blog.exceptions.EntryNotFoundException;
import com.project.blog.exceptions.InsufficientPermissionException;
import com.project.blog.payloads.CommentRequest;
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
    public Comment newComment(Long postId, CommentRequest commentRequest, String user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntryNotFoundException(
                "post with ID " + postId + " does not exist"));
        BlogUser currentLoggedInUser = userRepository.findByUsername(user)
                .orElseThrow(() -> new EntryNotFoundException("User does not exist"));
        return commentRepository.save(new Comment(
                null, commentRequest.getContent(), LocalDateTime.now(), null, post, currentLoggedInUser));
    }

    @Override
    public Comment getComment(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new EntryNotFoundException("Comment with ID " + id + " does not exist"));
    }

    @Transactional
    @Override
    public Comment updateComment(Long id, CommentRequest commentRequest, String user) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(
                "Comment with ID " + id + " does not exist"));

        if(user.equals(comment.getCreator().getUsername())){
            comment.setContent(commentRequest.getContent());
            comment.setDateEdited(LocalDateTime.now());
            return comment;
        }
        throw new InsufficientPermissionException("Sorry you can't edit this comment");
    }

    @Override
    public void deleteComment(Long id, Authentication authentication) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(
                "Comment with ID " + id + " does not exist"));

        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("comment:write")) ||
                authentication.getName().equals(comment.getCreator().getUsername())){
            commentRepository.delete(comment);
        }else{
            throw new InsufficientPermissionException("Sorry you can't delete this comment");
        }
    }
}
