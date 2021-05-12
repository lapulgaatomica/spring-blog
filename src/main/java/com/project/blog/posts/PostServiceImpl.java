package com.project.blog.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public List<Post> getBlogPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post newBlogPost(PostDTO postDTO) {
        return postRepository.save(new Post(
                null, postDTO.getTitle(), postDTO.getContent(),
                LocalDateTime.now(), null));
    }

    @Override
    public Optional<Post> getBlogPost(Long id) {
        return postRepository.findById(id);
    }

    @Transactional
    @Override
    public Optional<Post> updateBlogPost(Long id, Post post) {
        Post updatedPost = postRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Blog post with ID " + id + " does not exist"));
        updatedPost.setContent(post.getContent());
        updatedPost.setDateEdited(LocalDateTime.now());
        return Optional.of(updatedPost);
    }

    @Override
    public void deleteBlogPost(Long id) {
        postRepository.deleteById(id);
    }
}
