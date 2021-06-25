package com.project.blog.services;

import com.project.blog.entities.Post;
import com.project.blog.payloads.PostRequest;
import com.project.blog.payloads.PostWithCommentsResponse;

import java.util.List;

public interface PostService {
    List<Post> getBlogPosts();
    Post newBlogPost(PostRequest postRequest, String username);
    Post updateBlogPost(Long id, PostRequest post, String user);
    void deleteBlogPost(Long idr);
    PostWithCommentsResponse getBlogPostWithComment(Long id);
}
