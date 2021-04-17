package com.project.blog.blogposts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {
    private final PostsRepository postsRepository;

    @Override
    public List<Posts> getBlogPosts() {
        return postsRepository.findAll();
    }

    @Override
    public Posts newBlogPost(Posts post) {
        post.setDateCreated(LocalDateTime.now());
        return postsRepository.save(post);
    }

    @Override
    public Optional<Posts> getBlogPost(Long id) {
        return postsRepository.findById(id);
    }

    @Transactional
    @Override
    public Optional<Posts> updateBlogPost(Long id, Posts post) {
        Posts updatedPost = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Blog post with ID " + id + " does not exist"));
        updatedPost.setPost(post.getPost());
        updatedPost.setDateEdited(LocalDateTime.now());
        return Optional.of(updatedPost);
    }

    @Override
    public void deleteBlogPost(Long id) {
        postsRepository.deleteById(id);
    }
}
