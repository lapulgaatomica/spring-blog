package com.project.blog.controllers;

import com.project.blog.entities.BlogUser;
import com.project.blog.payloads.PostWithCommentsResponse;
import com.project.blog.entities.Post;
import com.project.blog.payloads.PostRequest;
import com.project.blog.security.JwtConfigProperties;
import com.project.blog.services.PostService;
import com.project.blog.services.UserDetailsServiceImpl;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(PostController.class)
public class PostControllerTest {

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private SecretKey secretKey;

    @MockBean
    private JwtConfigProperties jwtConfigProperties;

    @MockBean
    private PostService postService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<List<Post>> jsonPostResponseList;

    @Autowired
    private JacksonTester<Post> jsonPostResponse;

    @Autowired
    private JacksonTester<PostRequest> jsonPostRequest;

    @Autowired
    private JacksonTester<PostWithCommentsResponse> jsonPostWithCommentResponse;


    @Test
    public void getBlogPosts() throws Exception{
        BlogUser user = new BlogUser();
        Post post = new Post(1L,"title", "test post", LocalDateTime.now(), null, user);
        List<Post> newPosts = List.of(post);
        given(postService
                .getBlogPosts())
                .willReturn(newPosts);

        MockHttpServletResponse response = mvc.perform(
                get("/api/v1/posts")).andReturn().getResponse();

        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(
                jsonPostResponseList.write(
                        newPosts
                ).getJson());
    }

    @Test
    public void getBlogPost() throws Exception{
        PostWithCommentsResponse post = new PostWithCommentsResponse(1L,"title", "test post", LocalDateTime.now(), null, List.of());
        given(postService
                .getBlogPostWithComment(1L))
                .willReturn(post);

        MockHttpServletResponse response = mvc.perform(
                get("/api/v1/posts/1")).andReturn().getResponse();

        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(
                jsonPostWithCommentResponse.write(
                        post
                ).getJson());
    }

    @Test
    @WithMockUser
    public void newBlogPost() throws Exception{
        BlogUser user = new BlogUser();
        PostRequest post = new PostRequest("title", "test post");
        Post postWithDateAdded  = new Post(1L,"title", "test post", LocalDateTime.now(), null, user);

        given(postService
                .newBlogPost(post, user.getUsername()))
                .willReturn(postWithDateAdded);


        MockHttpServletResponse response = mvc.perform(
                post("/api/v1/posts").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPostRequest.write(post).getJson()))
                .andReturn().getResponse();

        then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        then(response.getContentAsString()).isEqualTo(
                jsonPostResponse.write(
                        postWithDateAdded
                ).getJson());
    }

    @Test
    @WithMockUser
    public  void updateBlogPost() throws Exception{
        BlogUser user = new BlogUser();
        PostRequest post = new PostRequest("title updated", "test post updated");
        Post expected = new Post(1L,"title updated", "test post updated", LocalDateTime.now(), LocalDateTime.now(), user);
        given(postService
                .updateBlogPost(1L, post, user.getUsername()))
                .willReturn(expected);

        MockHttpServletResponse response = mvc.perform(
                patch("/api/v1/posts/1").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPostRequest.write(post).getJson()))
                .andReturn().getResponse();

        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(
                jsonPostResponse.write(
                        expected
                ).getJson());
    }

    @Test
    @WithMockUser
    public void deleteBlogPost() throws Exception{
        MockHttpServletResponse response = mvc.perform(
                delete("/api/v1/posts/1")).andReturn().getResponse();

        then(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
