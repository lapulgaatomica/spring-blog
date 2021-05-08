package com.project.blog.blogposts;

import java.util.List;
import java.util.Optional;

public interface PostService {
    public List<Post> getBlogPosts();
    public Post newBlogPost(Post post);
    public Optional<Post> getBlogPost(Long id);
    public Optional<Post> updateBlogPost(Long id, Post post);
    public void deleteBlogPost(Long id);
}
