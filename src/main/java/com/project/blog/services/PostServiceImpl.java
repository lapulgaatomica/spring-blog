package com.project.blog.services;

import com.project.blog.entities.BlogUser;
import com.project.blog.entities.Comment;
import com.project.blog.entities.Post;
import com.project.blog.payloads.PostDTO;
import com.project.blog.payloads.PostWithCommentsDTO;
import com.project.blog.repositories.BlogUserRepository;
import com.project.blog.repositories.CommentRepository;
import com.project.blog.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        ).orElseThrow(() -> new IllegalStateException("User does not exist"));
        return postRepository.save(new Post(
                null, postDTO.getTitle(), postDTO.getContent(),
                LocalDateTime.now(), null, currentLoggedInUser));
    }

    @Override
    public PostWithCommentsDTO getBlogPostWithComment(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Blog post with ID " + id + " does not exist"));
        List<Comment> comments = commentRepository.findByPostId(id);

        return new PostWithCommentsDTO(
                post.getId(), post.getTitle(), post.getContent(), post.getDateCreated(),
                post.getDateEdited(), comments);
    }

    @Transactional
    @Override
    public Post updateBlogPost(Long id, PostDTO postDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Blog post with ID " + id + " does not exist"));
        Authentication currentlyLoggedInUser = SecurityContextHolder.getContext().getAuthentication();

        if(currentlyLoggedInUser.getName().equals(post.getCreator().getUsername())){
            post.setContent(postDTO.getContent());
            post.setTitle(postDTO.getTitle());
            post.setDateEdited(LocalDateTime.now());
            return post;
        }

        throw new IllegalStateException("Sorry you can't edit this post");
    }

    @Override
    public void deleteBlogPost(Long id) {
        Post post = postRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("Blog post with ID " + id + " does not exist"));
        Authentication currentlyLoggedInUser = SecurityContextHolder.getContext().getAuthentication();

        if(currentlyLoggedInUser.getAuthorities().contains(new SimpleGrantedAuthority("post:write")) ||
                currentlyLoggedInUser.getName().equals(post.getCreator().getUsername())){
            postRepository.delete(post);
        }else{
            throw new IllegalStateException("Sorry you can't delete this post");
        }
    }
}
