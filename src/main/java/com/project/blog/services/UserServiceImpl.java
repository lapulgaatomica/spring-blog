package com.project.blog.services;

import com.project.blog.entities.PasswordResetToken;
import com.project.blog.exceptions.*;
import com.project.blog.payloads.*;
import com.project.blog.entities.BlogUser;
import com.project.blog.entities.Role;
import com.project.blog.entities.enums.RoleName;
import com.project.blog.repositories.BlogUserRepository;
import com.project.blog.repositories.PasswordResetTokenRepository;
import com.project.blog.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.project.blog.entities.enums.RoleName.*;

@AllArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final BlogUserRepository blogUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public GenericResponse register(RegistrationRequest registrationRequest) {
        // Todo verify that email is valid

        boolean usernameAlreadyExists = blogUserRepository.findByUsername(registrationRequest.getUsername()).isPresent();
        boolean emailAlreadyExists = blogUserRepository.findByEmail(registrationRequest.getEmail()).isPresent();

        if(usernameAlreadyExists || emailAlreadyExists){
            throw new EntryAlreadyExistsException("Username or Email is already taken");
        }

        BlogUser user = new BlogUser();
        user.setUsername(registrationRequest.getUsername());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        Role role;

        if(blogUserRepository.count() == 0){//If there are no prior users, make this user an admin
            if(roleRepository.count() == 0){
                for(RoleName roleName: RoleName.values()){
                    roleRepository.save(new Role(roleName));
                }
            }
            role = roleRepository.findByName(SUPER_ADMIN).orElseThrow(
                    () -> new EntryNotFoundException("role with name " + SUPER_ADMIN.name() + " does not exist"));
        }else{//make this user an ordinary user
            role = roleRepository.findByName(USER).orElseThrow(
                    () -> new EntryNotFoundException("role with name " + USER.name() + " does not exist"));
        }

        user.setRole(role);
        blogUserRepository.save(user);

        return new GenericResponse(true, user.getEmail());
    }

    @Override
    public List<Role> getRoles(Collection<? extends GrantedAuthority> authorities) {
        if(authorities.contains(new SimpleGrantedAuthority("ROLE_" + SUPER_ADMIN.name()))){
            return roleRepository.findAll();
        }
        throw new InsufficientPermissionException("You're not an admin");
    }

    @Override
    public GenericResponse changeRole(String username,
                                      ChangeRoleRequest changeRoleRequest,
                                      Authentication authentication) {


        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + SUPER_ADMIN.name()))){
            BlogUser user = blogUserRepository.findByUsername(username).orElseThrow(
                    () -> new EntryNotFoundException("User " + username + " not found"));
            user.setRole(roleRepository.findByName(changeRoleRequest.getRole()).orElseThrow(
                            () -> new EntryNotFoundException("role with name " + changeRoleRequest.getRole() + " does not exist")));
            blogUserRepository.save(user);
            return new GenericResponse(true,
                    username + "'s role was successfully changed to " + changeRoleRequest.getRole());
        }

        throw new InsufficientPermissionException("You're not an admin");
    }

    @Override
    public GenericResponse changePassword(Long id,
                                          PasswordChangeRequest request,
                                          String nameOfCurrentlyLoggedInUser) {
        BlogUser user = blogUserRepository.findById(id).orElseThrow(
                () -> new EntryNotFoundException("User with id " + id + " does not exist"));

        if(nameOfCurrentlyLoggedInUser.equals(user.getUsername())){
            if(passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
                if(request.getNewPassword1().equals(request.getNewPassword2())){
                    String newPassword = passwordEncoder.encode(request.getNewPassword1());
                    user.setPassword(newPassword);
                    blogUserRepository.save(user);
                    return new GenericResponse(true, "password successfully changed");
                }else{
                    throw new PasswordMismatchException("Please enter your new password again twice and ensure they match");
                }
            }else{
                // Todo: I have to find a better way to go about incorrect password in case
                // Todo: the account is being accessed by someone who isn't the account owner
                throw new PasswordMismatchException("Please enter your old password");
            }
        }

        throw new InsufficientPermissionException("You can't change another user's password");
    }

    @Override
    public GenericResponse generatePasswordResetToken(String email) {
        BlogUser user = blogUserRepository.findByEmail(email)
                .orElseThrow(() -> new EntryNotFoundException("No user was found with the email " + email));
        String token = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(10);
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, expiresAt);

        passwordResetTokenRepository.save(passwordResetToken);
        log.info("127.0.0.1:8080/api/v1/users/password/reset?token=" + token);
        return new GenericResponse(true, "please check your email for steps to reset your password");
    }

    @Override
    public GenericResponse resetPassword(String token) {
        Optional<PasswordResetToken> passwordResetTokenOptional = passwordResetTokenRepository.findByToken(token);

        if(passwordResetTokenOptional.isPresent()){
            log.info("127.0.0.1:8080/api/v1/users/password/reset/confirm?token=" + token);
            return new GenericResponse(true, "Please reset your password");
        }else{
            // Todo: Invalid token exception
            throw new EntryNotFoundException("Invalid token");
        }
    }

    @Override
    public GenericResponse confirmPasswordReset(PasswordResetRequest request, String token) {
        PasswordResetToken resetToken = passwordResetTokenRepository
                .findByToken(token).orElseThrow(() -> new EntryNotFoundException("Token " + token + " not found"));
        BlogUser user = resetToken.getUser();

        if(resetToken.getExpiresAt().isAfter(LocalDateTime.now())){
            if(request.getPassword1().equals(request.getPassword2())){
                String newPassword = passwordEncoder.encode(request.getPassword1());
                user.setPassword(newPassword);
                blogUserRepository.save(user);
                passwordResetTokenRepository.delete(resetToken);
                return new GenericResponse(true, "password successfully changed");
            }else{
                throw new PasswordMismatchException("Please enter your new password again twice and ensure they match");
            }
        }else{
          throw new InvalidTokenException("Token has expired");
        }
    }
}
