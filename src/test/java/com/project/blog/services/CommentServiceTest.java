package com.project.blog.services;

import com.project.blog.entities.BlogUser;
import com.project.blog.entities.Comment;
import com.project.blog.entities.Post;
import com.project.blog.payloads.CommentRequest;
import com.project.blog.repositories.BlogUserRepository;
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

    @Mock
    private BlogUserRepository userRepository;

    private CommentService commentService;

    @BeforeEach
    void setUp(){
        commentService = new CommentServiceImpl(commentRepository, postRepository, userRepository);
    }

    @Test
    public void addNewComment(){
        // Given
        BlogUser user = new BlogUser();
        Post post = new Post(1L, "post title", "post", LocalDateTime.now(), null, user);
        CommentRequest commentRequest = new CommentRequest();

        Comment comment = new Comment(1L, "content", post, user);
        given(postRepository.findById(1L)).willReturn(Optional.of(post));

        // When
        commentService.newComment(1L, commentRequest);

        // Then
        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(commentArgumentCaptor.capture());
        Comment capturedComment = commentArgumentCaptor.getValue();
        assertThat(capturedComment.getContent()).isEqualTo(commentRequest.getContent());
//        assertThat(capturedComment.getPost().getId()).isEqualTo(commentRequest.getPostId());
    }

    @Test
    public void canGetComment(){
        // Given
        BlogUser user = new BlogUser();
        Post post = new Post(1L, "post title", "post", LocalDateTime.now(), null, user);
        Comment comment = new Comment(1L, "content", post, user);
        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));

        // When
        commentService.getComment(1L);

        // Then
        verify(commentRepository).findById(1L);
    }

    @Test
    public void canUpdateComment(){
        // Given
        BlogUser user = new BlogUser();
        Post post = new Post(1L, "post title", "post", LocalDateTime.now(), null, user);
        CommentRequest commentRequest = new CommentRequest();

        Comment comment = new Comment(1L, "content", post, user);
        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));

        // When
        commentService.updateComment(1L, commentRequest);

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