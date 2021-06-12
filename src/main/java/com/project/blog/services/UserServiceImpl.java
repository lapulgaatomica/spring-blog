package com.project.blog.services;

import com.project.blog.exceptions.EntryNotFoundException;
import com.project.blog.payloads.ChangeRoleRequest;
import com.project.blog.entities.BlogUser;
import com.project.blog.payloads.PasswordChangeRequest;
import com.project.blog.payloads.RegistrationRequest;
import com.project.blog.entities.Role;
import com.project.blog.entities.enums.RoleName;
import com.project.blog.repositories.BlogUserRepository;
import com.project.blog.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.blog.entities.enums.RoleName.*;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final BlogUserRepository blogUserRepository;
    private final RoleRepository roleRepository;

    @Override
    public String register(RegistrationRequest registrationRequest) {
        // Todo verify that email is valid

        boolean usernameAlreadyExists = blogUserRepository.findByUsername(registrationRequest.getUsername()).isPresent();
        boolean emailAlreadyExists = blogUserRepository.findByEmail(registrationRequest.getEmail()).isPresent();

        if(usernameAlreadyExists || emailAlreadyExists){
            throw new IllegalStateException("Username or Email is already taken");
        }

        BlogUser user = new BlogUser();
        user.setUsername(registrationRequest.getUsername());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        Role role;

        if(blogUserRepository.count() == 0){
            if(roleRepository.count() == 0){
                for(RoleName roleName: RoleName.values()){
                    roleRepository.save(new Role(roleName));
                }
            }
            role = roleRepository.findByName(SUPER_ADMIN).orElseThrow(
                    () -> new IllegalStateException("role with name " + SUPER_ADMIN.name() + " does not exist"));
        }else{
            role = roleRepository.findByName(USER).orElseThrow(
                    () -> new IllegalStateException("role with name " + USER.name() + " does not exist"));
        }
        user.setRole(role);
        blogUserRepository.save(user);
        return user.getEmail();
    }

    @Override
    public List<Role> getRoles() {
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(
                new SimpleGrantedAuthority("ROLE_" + SUPER_ADMIN.name()))){
            return roleRepository.findAll();
        }
        throw new IllegalStateException("You're not an admin");
    }

    @Override
    public String changeRole(String username, ChangeRoleRequest changeRoleRequest) {
        Authentication currentlyLoggedInUser = SecurityContextHolder.getContext().getAuthentication();

        if(currentlyLoggedInUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + SUPER_ADMIN.name()))){
            BlogUser user = blogUserRepository.findByUsername(username).orElseThrow(
                    () -> new IllegalStateException("User " + username + " not found"));
            user.setRole(roleRepository.findByName(changeRoleRequest.getRole()).orElseThrow(
                            () -> new IllegalStateException("role with name " + changeRoleRequest.getRole() + " does not exist")));
            blogUserRepository.save(user);
            return username + "'s role was successfully changed to " + changeRoleRequest.getRole();
        }

        throw new IllegalStateException("You're not an admin");
    }

    @Override
    public String changePassword(Long id, PasswordChangeRequest request) {
        Authentication currentlyLoggedInUser = SecurityContextHolder.getContext().getAuthentication();
        BlogUser user = blogUserRepository.findById(id).orElseThrow(
                () -> new EntryNotFoundException("User with id " + id + " does not exist"));
        if(currentlyLoggedInUser.getName().equals(user.getUsername())){
            if(passwordEncoder.matches(user.getPassword(), request.getOldPassword())){
                if(request.getNewPassword1().equals(request.getNewPassword2())){
                    String newPassword = passwordEncoder.encode(request.getNewPassword1());
                    user.setPassword(newPassword);
                    blogUserRepository.save(user);
                    return "Password successfully changed";
                }else{
                    throw new IllegalStateException("Please enter your new password again and ensure they match");
                }
            }else{
                // Todo: I have to find a better way to go about incorrect password in case
                // Todo: the account is being accessed by someone who isn't the account owner
                throw new IllegalStateException("Please enter your correct password");
            }
        }
        throw new IllegalStateException("You don't own this account");
    }
}
