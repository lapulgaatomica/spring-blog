package com.project.blog.repositories;

import com.project.blog.domain.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BlogUser,Long> {
    Optional<BlogUser> findByEmail(String email);
    Optional<BlogUser> findByUsername(String username);
}
