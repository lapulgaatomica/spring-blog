package com.project.blog.services;

import com.project.blog.domain.Comment;
import com.project.blog.domain.Post;
import com.project.blog.dtos.CommentDTO;
import com.project.blog.repositories.CommentRepository;
import com.project.blog.repositories.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    private CommentService commentService;

    @BeforeEach
    void setUp(){
        commentService = new CommentServiceImpl(commentRepository, postRepository);
    }

    @Test
    public void addNewComment(){
        // Given
        Post post = new Post(1L, "post title", "post", LocalDateTime.now(), null);
        CommentDTO commentDTO = new CommentDTO("content", 1L);

        Comment comment = new Comment(1L, "content", LocalDateTime.now(), null, post);
        given(postRepository.findById(1L)).willReturn(Optional.of(post));

        // When
        commentService.newComment(commentDTO);

        // Then
        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(commentArgumentCaptor.capture());
        Comment capturedComment = commentArgumentCaptor.getValue();
        assertThat(capturedComment.getContent()).isEqualTo(commentDTO.getContent());
        assertThat(capturedComment.getPost().getId()).isEqualTo(commentDTO.getPostId());
    }

    @Test
    public void canGetCommentsByPostId(){
        // When
        commentService.getCommentsByPostId(1L);

        // Then
        verify(commentRepository).findByPostId(1L);
    }

    @Test
    public void canGetComment(){
        // Given
        Post post = new Post(1L, "post title", "post", LocalDateTime.now(), null);
        Comment comment = new Comment(1L, "content", LocalDateTime.now(), null, post);
        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));

        // When
        commentService.getComment(1L);

        // Then
        verify(commentRepository).findById(1L);
    }

    @Test
    public void canUpdateComment(){
        // Given
        Post post = new Post(1L, "post title", "post", LocalDateTime.now(), null);
        CommentDTO commentDTO = new CommentDTO("content", 1L);

        Comment comment = new Comment(1L, "content", LocalDateTime.now(), null, post);
        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));

        // When
        commentService.updateComment(1L, commentDTO);

        // Then
        assertThat(comment);
    }

    @Test
    public void canDeleteComment(){
        // When
        commentService.deleteComment(1L);

        // Then
        verify(commentRepository).deleteById(1L);
    }
}