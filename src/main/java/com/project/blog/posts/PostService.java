package com.project.blog.posts;

import java.util.List;

public interface PostService {
    public List<Post> getBlogPosts();
    public Post newBlogPost(PostDTO postDTO);
    public Post getBlogPost(Long id);
    public Post updateBlogPost(Long id, PostDTO post);
    public void deleteBlogPost(Long id);
}
