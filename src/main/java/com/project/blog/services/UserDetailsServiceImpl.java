package com.project.blog.services;

import com.project.blog.entities.BlogUser;
import com.project.blog.repositories.BlogUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final BlogUserRepository blogUserRepository;

    public UserDetailsServiceImpl(BlogUserRepository blogUserRepository) {
        this.blogUserRepository = blogUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BlogUser user = blogUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found!"));

        return user;
    }
}
