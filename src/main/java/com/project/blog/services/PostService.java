package com.project.blog.services;

import com.project.blog.entities.Post;
import com.project.blog.payloads.PostRequest;
import com.project.blog.payloads.PostWithCommentsResponse;

import java.util.List;

public interface PostService {
    List<Post> getBlogPosts();
    Post newBlogPost(PostRequest postRequest);
    Post updateBlogPost(Long id, PostRequest post);
    void deleteBlogPost(Long id);
    PostWithCommentsResponse getBlogPostWithComment(Long id);
}
