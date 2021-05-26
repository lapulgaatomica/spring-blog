package com.project.blog.services;

import com.project.blog.entities.Comment;
import com.project.blog.entities.Post;
import com.project.blog.dtos.CommentDTO;
import com.project.blog.repositories.CommentRepository;
import com.project.blog.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public Comment newComment(CommentDTO commentDTO) {
        Post post = postRepository.findById(commentDTO.getPostId()).get();
        return commentRepository.save(new Comment(
                null, commentDTO.getContent(), LocalDateTime.now(), null, post));
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId){
        return commentRepository.findByPostId(postId);
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
