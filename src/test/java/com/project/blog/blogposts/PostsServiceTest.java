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
public class PostsServiceTest {

    @Mock
    private PostsRepository postsRepository;
    private PostsService postsService;

    @BeforeEach
    void setUp() {
        postsService = new PostsServiceImpl(postsRepository);
    }

    @Test
    public void getBlogPosts(){
        // When
        postsService.getBlogPosts();

        // Then
        verify(postsRepository).findAll();
    }

    @Test
    public void getBlogPost(){
        // When
        postsService.getBlogPost(1L);

        // Then
        verify(postsRepository).findById(1L);
    }

    @Test
    public void newBlogPost(){
        // Given
        Posts post = new Posts("blog post");

        // When
        postsService.newBlogPost(post);

        // Then
        verify(postsRepository).save(post);
    }

    @Test
    public void updateBlogPost(){
        long id =  1;
        Posts post = new Posts("blog post");
        // Given
        given(postsRepository.findById(id)).willReturn(Optional.of(post));

        // When
        postsService.updateBlogPost(id, post);

        //
        assertThat(Optional.of(post)).isPresent();
    }

    @Test
    public void deleteBlogPost(){
        // When
        postsService.deleteBlogPost(1L);

        // Then
        verify(postsRepository).deleteById(1L);
    }


}
