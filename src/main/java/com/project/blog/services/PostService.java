package com.project.blog.services;

import com.project.blog.entities.Post;
import com.project.blog.payloads.PostRequest;
import com.project.blog.payloads.PostWithCommentsDTO;

import java.util.List;

public interface PostService {
    List<Post> getBlogPosts();
    Post newBlogPost(PostRequest postRequest);
    Post updateBlogPost(Long id, PostRequest post);
    void deleteBlogPost(Long id);
    PostWithCommentsDTO getBlogPostWithComment(Long id);
}
