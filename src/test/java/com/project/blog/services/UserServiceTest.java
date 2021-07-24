package com.project.blog.services;

import com.project.blog.entities.BlogUser;
import com.project.blog.entities.PasswordResetToken;
import com.project.blog.entities.Role;
import com.project.blog.payloads.*;
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

import java.time.LocalDateTime;
import java.util.Optional;

import static com.project.blog.entities.enums.RoleName.SUPER_ADMIN;
import static com.project.blog.entities.enums.RoleName.USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

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

    @Mock
    private GenericResponse response;

    @Captor
    private ArgumentCaptor<BlogUser> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<PasswordResetToken> tokenArgumentCaptor;

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
        // When
        userService.getRoles();

        // Then
        verify(roleRepository).findAll();
    }

    @Test
    void changeRole() {
        // Given
        Role role = new Role(SUPER_ADMIN);
        BlogUser user = new BlogUser("username", "email@email.com", "password");
        user.setRole(new Role(USER));
        ChangeRoleRequest request = new ChangeRoleRequest();
        request.setRole(SUPER_ADMIN);
        given(blogUserRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        given(roleRepository.findByName(request.getRole())).willReturn(Optional.of(role));

        // When
        userService.changeRole(user.getUsername(), request);

        // Then
        then(blogUserRepository).should().save(userArgumentCaptor.capture());
        BlogUser userArgumentCaptorValue = userArgumentCaptor.getValue();
        assertThat(userArgumentCaptorValue).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void changePassword() {
        // Given
        BlogUser user = new BlogUser("username", "email@email.com", "password");
        PasswordChangeRequest request = new PasswordChangeRequest("old", "new", "new");
        given(blogUserRepository.findById(1L)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(request.getOldPassword(), user.getPassword())).willReturn(true);

        // When
        userService.changePassword(1L, request, user.getUsername());

        // Then
        then(blogUserRepository).should().save(userArgumentCaptor.capture());
        BlogUser userArgumentCaptorValue = userArgumentCaptor.getValue();
        assertThat(userArgumentCaptorValue).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void generatePasswordResetToken() {
        // Given
        BlogUser user = new BlogUser("user", "email@email.com", "password");
        given(blogUserRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));
        PasswordResetToken resetToken = new PasswordResetToken("token", user, LocalDateTime.now().plusMinutes(10));

        // When
        userService.generatePasswordResetToken(user.getEmail());

        // Then
        then(passwordResetTokenRepository).should().save(tokenArgumentCaptor.capture());
        PasswordResetToken tokenCaptorValue = tokenArgumentCaptor.getValue();
        assertThat(tokenCaptorValue).usingRecursiveComparison()
                .ignoringFieldsOfTypes(LocalDateTime.class) //Any date property has to be ignored
                .ignoringFields("token") //Token has to be ignored as well, because it's a random uuid value, not reproducible
                .isEqualTo(resetToken);
    }

    @Test
    void resetPassword() {
        // Given
        BlogUser user = new BlogUser("user", "email@email.com", "password");
        PasswordResetToken resetToken = new PasswordResetToken("token", user, LocalDateTime.now().plusMinutes(10));
        given(passwordResetTokenRepository.findByToken("token")).willReturn(Optional.of(resetToken));

        // When
        response = userService.resetPassword(resetToken.getToken());

        // Then
        verify(response.getSuccess()).equals(true);
    }

    @Test
    void confirmPasswordReset() {
        // Given
        BlogUser user = new BlogUser("user", "email@email.com", "password");
        PasswordResetToken resetToken = new PasswordResetToken("token", user, LocalDateTime.now().plusMinutes(10));
        given(passwordResetTokenRepository.findByToken(resetToken.getToken())).willReturn(Optional.of(resetToken));
        PasswordResetRequest request = new PasswordResetRequest("new-password", "new-password");

        // When
        userService.confirmPasswordReset(request, "token");

        // Then
        then(blogUserRepository).should().save(userArgumentCaptor.capture());
        BlogUser userArgumentCaptorValue = userArgumentCaptor.getValue();
        assertThat(userArgumentCaptorValue).usingRecursiveComparison().isEqualTo(user);
        verify(passwordResetTokenRepository).delete(resetToken);
    }
}