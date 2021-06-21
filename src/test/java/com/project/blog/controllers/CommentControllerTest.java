package com.project.blog.controllers;

import com.project.blog.entities.BlogUser;
import com.project.blog.entities.Comment;
import com.project.blog.entities.Post;
import com.project.blog.payloads.CommentRequest;
import com.project.blog.services.CommentService;
import com.project.blog.services.PostService;
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

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @MockBean
    private CommentService commentService;

    @MockBean
    private PostService postService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<List<Comment>> jsonCommentResponseList;

    @Autowired
    private JacksonTester<Comment> jsonCommentResponse;

    @Autowired
    private JacksonTester<CommentRequest> jsonCommentRequest;

    @Test
    public void newComment() throws Exception{
        // Given
        BlogUser user = new BlogUser();
        CommentRequest comment = new CommentRequest("Comment");
        Post post = new Post(1L, "Post Title", "Post Content", LocalDateTime.now(), null, user);
        Comment commentResponse = new Comment(1L, "Comment Content", post, user);
        given(commentService
                .newComment(1L, comment))
                .willReturn(commentResponse);

        // When
        MockHttpServletResponse response = mvc.perform(
                post("/comment").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCommentRequest.write(comment).getJson()))
                .andReturn().getResponse();

        // Then
        then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        then(response.getContentAsString()).isEqualTo(
                jsonCommentResponse.write(
                        commentResponse
                ).getJson()
        );
    }

    @Test
    public void getComment() throws Exception{
        // Given
        BlogUser user = new BlogUser();
        Post post = new Post(1L, "Post Title", "Post Content", LocalDateTime.now(), null, user);
        Comment commentResponse = new Comment(1L, "Comment Content", post, user);
        given(commentService
            .getComment(1L))
            .willReturn(commentResponse);

        // When
        MockHttpServletResponse response = mvc.perform(
                get("/comment/1")).andReturn().getResponse();

        // Then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(
                jsonCommentResponse.write(
                        commentResponse
                ).getJson());
    }

    @Test
    public void updateComment() throws Exception{
        // Given
        BlogUser user = new BlogUser();
        CommentRequest comment = new CommentRequest("Comment");
        Post post = new Post(1L, "Post Title", "Post Content", LocalDateTime.now(), null, user);
        Comment commentResponse = new Comment(1L, "Comment Content", LocalDateTime.now(), LocalDateTime.now(), post, user);

        given(commentService
            .updateComment(1L, comment))
            .willReturn(commentResponse);

        // When
        MockHttpServletResponse response = mvc.perform(
                patch("/comment/1").contentType(MediaType.APPLICATION_JSON)
                    .content(jsonCommentRequest.write(comment).getJson()))
                .andReturn().getResponse();

        // Then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(
                jsonCommentResponse.write(
                        commentResponse
                ).getJson());
    }

    @Test
    public void deleteComment() throws Exception{
        MockHttpServletResponse response = mvc.perform(
                delete("/comment/1")).andReturn().getResponse();

        then(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}