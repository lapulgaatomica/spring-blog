package com.project.blog.services;

import com.project.blog.entities.BlogUser;
import com.project.blog.entities.Role;
import com.project.blog.entities.enums.RoleName;
import com.project.blog.payloads.RegistrationRequest;
import com.project.blog.repositories.BlogUserRepository;
import com.project.blog.repositories.PasswordResetTokenRepository;
import com.project.blog.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.project.blog.entities.enums.RoleName.SUPER_ADMIN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private BlogUserRepository blogUserRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Captor
    private ArgumentCaptor<BlogUser> userArgumentCaptor;

    private UserService userService;

    @BeforeEach
    void setUp(){
        userService =
                new UserServiceImpl(passwordEncoder, blogUserRepository, roleRepository, passwordResetTokenRepository);
    }

    @Test
    void register() {
        // Given
        String username = "user";
        String email = "email@email.com";
        String password = "password";
        String hashedPassword = "hashedpassword";
        Role superAdminRole = new Role(SUPER_ADMIN);
        RegistrationRequest registrationRequest = new RegistrationRequest(username, email, password);
        BlogUser user = new BlogUser(username, email, hashedPassword);
        user.setRole(superAdminRole);
        given(blogUserRepository.findByUsername(registrationRequest.getUsername())).willReturn(Optional.empty());
        given(blogUserRepository.findByEmail(registrationRequest.getEmail())).willReturn(Optional.empty());
        given(passwordEncoder.encode(registrationRequest.getPassword())).willReturn("hashedpassword");
        given(roleRepository.findByName(SUPER_ADMIN)).willReturn(Optional.of(superAdminRole));

        // When
        userService.register(registrationRequest);

        // Then
        then(blogUserRepository).should().save(userArgumentCaptor.capture());
        BlogUser userArgumentCaptorValue = userArgumentCaptor.getValue();
        assertThat(userArgumentCaptorValue).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void getRoles() {
    }

    @Test
    void changeRole() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void generatePasswordResetToken() {
    }

    @Test
    void resetPassword() {
    }

    @Test
    void confirmPasswordReset() {
    }
}