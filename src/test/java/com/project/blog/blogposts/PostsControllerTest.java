package com.project.blog.blogposts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
}
