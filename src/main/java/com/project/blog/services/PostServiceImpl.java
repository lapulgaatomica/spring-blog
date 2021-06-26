package com.project.blog.services;

import com.project.blog.entities.BlogUser;
import com.project.blog.entities.Comment;
import com.project.blog.entities.Post;
import com.project.blog.exceptions.EntryNotFoundException;
import com.project.blog.exceptions.InsufficientPermissionException;
import com.project.blog.payloads.PostRequest;
import com.project.blog.payloads.PostWithCommentsResponse;
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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    public Post newBlogPost(PostRequest postRequest, String username) {
        BlogUser currentLoggedInUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntryNotFoundException("User does not exist"));

        return postRepository.save(new Post(
                null, postRequest.getTitle(), postRequest.getContent(),
                LocalDateTime.now(), null, currentLoggedInUser));
    }

    @Override
    public PostWithCommentsResponse getBlogPostWithComment(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new EntryNotFoundException("Blog post with ID " + id + " does not exist"));
        List<Comment> comments = commentRepository.findByPostId(id);

        return new PostWithCommentsResponse(
                post.getId(), post.getTitle(), post.getContent(), post.getDateCreated(),
                post.getDateEdited(), comments);
    }

    @Transactional
    @Override
    public Post updateBlogPost(Long id, PostRequest postRequest, String user) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntryNotFoundException("Blog post with ID " + id + " does not exist"));

        if(user.equals(post.getCreator().getUsername())){
            post.setContent(postRequest.getContent());
            post.setTitle(postRequest.getTitle());
            post.setDateEdited(LocalDateTime.now());
            return post;
        }

        throw new InsufficientPermissionException("Sorry you can't edit this post");
    }

    @Override
    public void deleteBlogPost(Long id, Authentication currentlyLoggedInUser) {
        Post post = postRepository.findById(id).
                orElseThrow(() -> new EntryNotFoundException("Blog post with ID " + id + " does not exist"));


        if(currentlyLoggedInUser.getAuthorities().contains(new SimpleGrantedAuthority("post:write")) ||
                currentlyLoggedInUser.getName().equals(post.getCreator().getUsername())){
            postRepository.delete(post);
        }else{
            throw new InsufficientPermissionException("Sorry you can't delete this post");
        }
    }
}
