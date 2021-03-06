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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BlogUserRepository userRepository;

    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;

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
    public void newBlogPost(){
        // Given
        BlogUser user = new BlogUser(1L, "user", "user@user.com", "password", null);
        PostRequest postRequest = new PostRequest("title","blog post");
        given(userRepository.findByUsername(user.getUsername()))
                .willReturn(Optional.of(user));

        Post post = new Post(null, postRequest.getTitle(),
                postRequest.getContent(), LocalDateTime.now(), null, user);

        // When
        postService.newBlogPost(postRequest, "user");

        // Then
        then(postRepository).should().save(postArgumentCaptor.capture());
        Post postArgumentCaptorValue = postArgumentCaptor.getValue();
        assertThat(postArgumentCaptorValue).usingRecursiveComparison()
                .ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(post);
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
        // Given
        long id =  1;
        PostRequest postRequest = new PostRequest("title edited", "blog post edited");
        BlogUser user = new BlogUser("user", "user@user.com", "password");
        Post post = new Post(null, "title", "content", LocalDateTime.now(), null, user);
        given(postRepository.findById(id)).willReturn(Optional.of(post));

        // When
        Post updatedPost = postService.updateBlogPost(id, postRequest, user.getUsername());

        // Then
        assertThat(updatedPost.getTitle()).isEqualTo(postRequest.getTitle());
        assertThat(updatedPost.getContent()).isEqualTo(postRequest.getContent());
        assertThat(updatedPost.getDateEdited()).isAfter(post.getDateCreated());
    }

    @ParameterizedTest
    @CsvSource({
            "user,ROLE_USER",
            "postwriter,post:write"
    })
    public void ownerOfPostOrUserWithSufficientPermissionCanDeleteBlogPost(
            String nameOfCurrentlyLoggedInUSer, String roleOfCurrentlyLoggedInUser){
        // given
        BlogUser user = new BlogUser(1L, "user", "user@user.com", "password", null);
        Post post = new Post(null, "title", "blog post", LocalDateTime.now(), null, user);
        given(postRepository.findById(1L)).willReturn(Optional.of(post));

        // when
        postService.deleteBlogPost(1L, nameOfCurrentlyLoggedInUSer,
                Set.of(new SimpleGrantedAuthority(roleOfCurrentlyLoggedInUser)));
        //todo write test to test that someone without sufficient permission can't delete a post

        // Then
        verify(postRepository).delete(post);
    }
}
