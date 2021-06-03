package com.project.blog.repositories;

import com.project.blog.entities.Role;
import com.project.blog.entities.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
