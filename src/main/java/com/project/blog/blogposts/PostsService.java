package com.project.blog.blogposts;

import java.util.List;
import java.util.Optional;

public interface PostsService {
    public List<Posts> getBlogPosts();
    public Posts newBlogPost(Posts post);
    public Optional<Posts> getBlogPost(Long id);
    public Optional<Posts> updateBlogPost(Long id, Posts post);
    public void deleteBlogPost(Long id);
}
