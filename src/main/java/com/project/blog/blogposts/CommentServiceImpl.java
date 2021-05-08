package com.project.blog.blogposts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public Comment newComment(CommentDTO commentDTO) {
        Post post = postRepository.findById(commentDTO.getPostId()).get();
        return commentRepository.save(new Comment(null, commentDTO.getContent(), post));
    }

    @Override
    public Optional<Comment> getComment(Long id) {
        return commentRepository.findById(id);
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
