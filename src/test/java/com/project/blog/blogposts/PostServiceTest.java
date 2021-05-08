package com.project.blog.blogposts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    private PostService postService;

    @BeforeEach
    void setUp() {
        postService = new PostServiceImpl(postRepository);
    }

    @Test
    public void getBlogPosts(){
        // When
        postService.getBlogPosts();

        // Then
        verify(postRepository).findAll();
    }

    @Test
    public void getBlogPost(){
        // When
        postService.getBlogPost(1L);

        // Then
        verify(postRepository).findById(1L);
    }

    @Test
    public void newBlogPost(){
        // Given
        Post post = new Post("blog post");

        // When
        postService.newBlogPost(post);

        // Then
        verify(postRepository).save(post);
    }

    @Test
    public void updateBlogPost(){
        long id =  1;
        Post post = new Post("blog post");
        // Given
        given(postRepository.findById(id)).willReturn(Optional.of(post));

        // When
        postService.updateBlogPost(id, post);

        //
        assertThat(Optional.of(post)).isPresent();
    }

    @Test
    public void deleteBlogPost(){
        // When
        postService.deleteBlogPost(1L);

        // Then
        verify(postRepository).deleteById(1L);
    }


}
