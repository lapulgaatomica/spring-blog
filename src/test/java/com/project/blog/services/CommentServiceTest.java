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
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

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

    @Captor
    private ArgumentCaptor<Comment> commentArgumentCaptor;

    private CommentService commentService;

    @BeforeEach
    void setUp(){
        commentService = new CommentServiceImpl(commentRepository, postRepository, userRepository);
    }

    @Test
    public void canAddNewComment(){
        // Given
        BlogUser user = new BlogUser(1L, "user", "user@user.com", "password", null);
        Post post = new Post(1L, "post title", "post", LocalDateTime.now(), null, user);
        CommentRequest commentRequest = new CommentRequest("content");
        Comment comment = new Comment(null, commentRequest.getContent(), post, user);
        given(postRepository.findById(1L)).willReturn(Optional.of(post));
        given(userRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));

        // When
        commentService.newComment(1L, commentRequest, user.getUsername());

        // Then
        verify(commentRepository).save(commentArgumentCaptor.capture());
        Comment commentArgumentCaptorValue = commentArgumentCaptor.getValue();
        assertThat(commentArgumentCaptorValue)
                .usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(comment);
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
        BlogUser user = new BlogUser("user", "user@user.com", "password");
        Post post = new Post(1L, "post title", "post", LocalDateTime.now(), null, user);
        CommentRequest commentRequest = new CommentRequest("updated comment");
        Comment comment = new Comment(1L, "comment", post, user);
        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));

        // When
        Comment updatedComment = commentService.updateComment(1L, commentRequest, user.getUsername());

        // Then
        assertThat(updatedComment.getContent()).isEqualTo(commentRequest.getContent());
        assertThat(updatedComment.getDateEdited()).isAfter(post.getDateCreated());
    }

    @Test
    public void canDeleteComment(){
        // given
        BlogUser user = new BlogUser(1L, "user", "user@user.com", "password", null);
        Post post = new Post(null, "title", "blog post", LocalDateTime.now(), null, user);
        Comment comment = new Comment(null, "content", post, user);
        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));

        // When
        commentService.deleteComment(1L, user.getUsername(), Set.of(new SimpleGrantedAuthority("comment:write")));

        // Then
        verify(commentRepository).delete(comment);
    }
}