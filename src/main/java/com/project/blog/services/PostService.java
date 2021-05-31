package com.project.blog.services;

import com.project.blog.entities.Post;
import com.project.blog.dtos.PostDTO;
import com.project.blog.dtos.PostWithCommentsDTO;

import java.util.List;

public interface PostService {
    List<Post> getBlogPosts();
    Post newBlogPost(PostDTO postDTO);
    Post updateBlogPost(Long id, PostDTO post);
    void deleteBlogPost(Long id);
    PostWithCommentsDTO getBlogPostWithComment(Long id);
}
