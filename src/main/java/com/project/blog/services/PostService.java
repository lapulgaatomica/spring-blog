package com.project.blog.services;

import com.project.blog.entities.Post;
import com.project.blog.payloads.PostRequest;
import com.project.blog.payloads.PostWithCommentsResponse;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface PostService {
    List<Post> getBlogPosts();
    Post newBlogPost(PostRequest postRequest, String username);
    Post updateBlogPost(Long id, PostRequest post, String user);
    void deleteBlogPost(Long id, String nameOfCurrentlyLoggedInUser, Collection<? extends GrantedAuthority> authoritiesOfCurrentlyLoggedInUser);
    PostWithCommentsResponse getBlogPostWithComment(Long id);
}
