package com.project.blog.services;

import com.project.blog.entities.BlogUser;
import com.project.blog.entities.Post;
import com.project.blog.payloads.PostRequest;
import com.project.blog.repositories.BlogUserRepository;
import com.project.blog.repositories.CommentRepository;
import com.project.blog.repositories.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private BlogUserRepository userRepository;
    private PostService postService;

    @BeforeEach
    void setUp() {
        postService = new PostServiceImpl(postRepository, commentRepository, userRepository);
    }

    @Test
    public void getBlogPosts(){
        // When
        postService.getBlogPosts();

        // Then
        verify(postRepository).findAll();
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void newBlogPost(){
        // Given
        BlogUser user = new BlogUser("user", "user@user.com", "password");
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        given(securityContext.getAuthentication()).willReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        given(authentication.getPrincipal()).willReturn(user);
        given(userRepository.findByUsername("user")).willReturn(Optional.of(user));
        PostRequest postRequest = new PostRequest("title","blog post");

        Post post = new Post(null, postRequest.getTitle(), postRequest.getContent(), LocalDateTime.now(), null, user);

        // When
        postService.newBlogPost(postRequest);

        // Then
        verify(postRepository).save(post);
    }

    @Test
    public void getBlogPostWithComment(){
        long id =  1;
        PostRequest postDTO = new PostRequest("title", "blog post");
        BlogUser user = new BlogUser();
        Post post = new Post(null, postDTO.getTitle(), postDTO.getContent(), LocalDateTime.now(), null, user);
        // Given
        given(postRepository.findById(id)).willReturn(Optional.of(post));
        given(commentRepository.findByPostId(id)).willReturn(List.of());

        // When
        postService.getBlogPostWithComment(1L);

        // Then
        verify(postRepository).findById(1L);
    }

    @Test
    public void updateBlogPost(){
        long id =  1;
        PostRequest postDTO = new PostRequest("title", "blog post");
        BlogUser user = new BlogUser();
        Post post = new Post(null, postDTO.getTitle(), postDTO.getContent(), LocalDateTime.now(), null, user);
        // Given
        given(postRepository.findById(id)).willReturn(Optional.of(post));

        // When
        postService.updateBlogPost(id, postDTO);

        //
        assertThat(post);
    }

    @Test
    public void deleteBlogPost(){
        // When
        postService.deleteBlogPost(1L);

        // Then
        verify(postRepository).deleteById(1L);
    }
}
