package com.project.blog.blogposts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(PostsController.class)
public class PostsControllerTest {

    @MockBean
    private PostsService postsService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<List<Posts>> jsonBlogPostsList;

    @Autowired
    private JacksonTester<Optional<Posts>> jsonOptionalBlogPost;

    @Autowired
    private JacksonTester<Posts> jsonBlogPost;


    @Test
    public void getBlogPosts() throws Exception{
        Posts posts = new Posts("test post");
        List<Posts> newPosts = List.of(posts);
        given(postsService
                .getBlogPosts())
                .willReturn(newPosts);

        MockHttpServletResponse response = mvc.perform(
                get("/posts")).andReturn().getResponse();

        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(
                jsonBlogPostsList.write(
                        newPosts
                ).getJson());
    }

    @Test
    public void getBlogPost() throws Exception{
        Posts posts = new Posts("test post");
        Optional<Posts> optionalPost = Optional.of(posts);
        given(postsService
                .getBlogPost(1L))
                .willReturn(optionalPost);

        MockHttpServletResponse response = mvc.perform(
                get("/posts/1")).andReturn().getResponse();

        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(
                jsonOptionalBlogPost.write(
                        optionalPost
                ).getJson());
    }

    @Test
    public void newBlogPost() throws Exception{
        Posts post = new Posts("test post");
        Posts postWithDateAdded  = new Posts("test post");
        postWithDateAdded.setDateCreated(post.getDateCreated());
        given(postsService
                .newBlogPost(post))
                .willReturn(postWithDateAdded);

        MockHttpServletResponse response = mvc.perform(
                post("/posts").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBlogPost.write(post).getJson()))
                        .andReturn().getResponse();

        then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        then(response.getContentAsString()).isEqualTo(
                jsonBlogPost.write(
                        postWithDateAdded
                ).getJson());
    }

    @Test
    public  void updateBlogPost() throws Exception{
        Posts post = new Posts("test post");
        postsService.newBlogPost(post);
        Optional<Posts> expected = Optional.of(new Posts(1L, "test post updated", LocalDateTime.now(), LocalDateTime.now()));
        given(postsService
                .updateBlogPost(1L, post))
                .willReturn(expected);

        MockHttpServletResponse response = mvc.perform(
                patch("/posts/1").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBlogPost.write(post).getJson()))
                        .andReturn().getResponse();

        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(
                jsonOptionalBlogPost.write(
                        expected
                ).getJson());
    }
}
