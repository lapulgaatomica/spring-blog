package com.project.blog.comments;

import com.project.blog.posts.Post;
import com.project.blog.posts.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Optional<Comment> getComment(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId){
        return commentRepository.findByPostId(postId);
    }

    @Override
    public Optional<Comment> updateComment(Long id, Comment comment) {
        return Optional.empty();
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
