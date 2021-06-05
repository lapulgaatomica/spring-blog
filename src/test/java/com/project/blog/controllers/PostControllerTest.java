//package com.project.blog.controllers;
//
//import com.project.blog.dtos.PostWithCommentsDTO;
//import com.project.blog.entities.Post;
//import com.project.blog.dtos.PostDTO;
//import com.project.blog.services.PostService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.json.JacksonTester;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.assertj.core.api.BDDAssertions.then;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//
//@ExtendWith(SpringExtension.class)
//@AutoConfigureJsonTesters
//@WebMvcTest(PostController.class)
//public class PostControllerTest {
//
//    @MockBean
//    private PostService postService;
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private JacksonTester<List<Post>> jsonPostResponseList;
//
//    @Autowired
//    private JacksonTester<Post> jsonPostResponse;
//
//    @Autowired
//    private JacksonTester<PostDTO> jsonPostRequest;
//
//    @Autowired
//    private JacksonTester<PostWithCommentsDTO> jsonPostWithCommentResponse;
//
//
//    @Test
//    @WithUserDetails("dele")
//    public void getBlogPosts() throws Exception{
//        Post post = new Post(1L,"title", "test post", LocalDateTime.now(), null);
//        List<Post> newPosts = List.of(post);
//        given(postService
//                .getBlogPosts())
//                .willReturn(newPosts);
//
//        MockHttpServletResponse response = mvc.perform(
//                get("/posts")).andReturn().getResponse();
//
//        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        then(response.getContentAsString()).isEqualTo(
//                jsonPostResponseList.write(
//                        newPosts
//                ).getJson());
//    }
//
//    @Test
//    public void getBlogPost() throws Exception{
//        PostWithCommentsDTO post = new PostWithCommentsDTO(1L,"title", "test post", LocalDateTime.now(), null, List.of());
//        given(postService
//                .getBlogPostWithComment(1L))
//                .willReturn(post);
//
//        MockHttpServletResponse response = mvc.perform(
//                get("/posts/1")).andReturn().getResponse();
//
//        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        then(response.getContentAsString()).isEqualTo(
//                jsonPostWithCommentResponse.write(
//                        post
//                ).getJson());
//    }
//
//    @Test
//    public void newBlogPost() throws Exception{
//        PostDTO post = new PostDTO("title", "test post");
//        Post postWithDateAdded  = new Post(1L,"title", "test post", LocalDateTime.now(), null);
//
//        given(postService
//                .newBlogPost(post))
//                .willReturn(postWithDateAdded);
//
//        MockHttpServletResponse response = mvc.perform(
//                post("/posts").contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonPostRequest.write(post).getJson()))
//                .andReturn().getResponse();
//
//        then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
//        then(response.getContentAsString()).isEqualTo(
//                jsonPostResponse.write(
//                        postWithDateAdded
//                ).getJson());
//    }
//
//    @Test
//    public  void updateBlogPost() throws Exception{
//        PostDTO post = new PostDTO("title updated", "test post updated");
//        Post expected = new Post(1L,"title updated", "test post updated", LocalDateTime.now(), LocalDateTime.now());
//        given(postService
//                .updateBlogPost(1L, post))
//                .willReturn(expected);
//
//        MockHttpServletResponse response = mvc.perform(
//                patch("/posts/1").contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonPostRequest.write(post).getJson()))
//                .andReturn().getResponse();
//
//        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        then(response.getContentAsString()).isEqualTo(
//                jsonPostResponse.write(
//                        expected
//                ).getJson());
//    }
//
//    @Test
//    public void deleteBlogPost() throws Exception{
//        MockHttpServletResponse response = mvc.perform(
//                delete("/posts/1")).andReturn().getResponse();
//
//        then(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
//    }
//}
