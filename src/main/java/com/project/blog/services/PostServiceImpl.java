package com.project.blog.services;

import com.project.blog.entities.BlogUser;
import com.project.blog.entities.Comment;
import com.project.blog.entities.Post;
import com.project.blog.dtos.PostDTO;
import com.project.blog.dtos.PostWithCommentsDTO;
import com.project.blog.repositories.BlogUserRepository;
import com.project.blog.repositories.CommentRepository;
import com.project.blog.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final BlogUserRepository userRepository;

    @Override
    public List<Post> getBlogPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post newBlogPost(PostDTO postDTO) {
        BlogUser currentLoggedInUser = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
        ).get();
        return postRepository.save(new Post(
                null, postDTO.getTitle(), postDTO.getContent(),
                LocalDateTime.now(), null, currentLoggedInUser));
    }

    @Override
    public PostWithCommentsDTO getBlogPostWithComment(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Blog post with ID " + id + " does not exist"));
        List<Comment> comments = commentRepository.findByPostId(id);

        return new PostWithCommentsDTO(
                post.getId(), post.getTitle(), post.getContent(), post.getDateCreated(),
                post.getDateEdited(), comments);
    }

    @Transactional
    @Override
    public Post updateBlogPost(Long id, PostDTO post) {
        Post updatedPost = postRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Blog post with ID " + id + " does not exist"));
        updatedPost.setContent(post.getContent());
        updatedPost.setTitle(post.getTitle());
        updatedPost.setDateEdited(LocalDateTime.now());
        return updatedPost;
    }

    @Override
    public void deleteBlogPost(Long id) {
        postRepository.deleteById(id);
    }
}
