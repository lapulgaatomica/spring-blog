package com.project.blog.controllers;

import com.project.blog.entities.Role;
import com.project.blog.entities.enums.RoleName;
import com.project.blog.payloads.*;
import com.project.blog.security.JwtConfigProperties;
import com.project.blog.services.UserDetailsServiceImpl;
import com.project.blog.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;

import java.util.*;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private SecretKey secretKey;

    @MockBean
    private JwtConfigProperties jwtConfigProperties;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<RegistrationRequest> jsonRegistrationRequest;

    @Autowired
    private JacksonTester<GenericResponse> jsonGenericResponse;

    @Autowired
    private JacksonTester<List<Role>> jsonRolesResponse;

    @Autowired
    private JacksonTester<ChangeRoleRequest> jsonChangeRoleRequest;

    @Autowired
    private JacksonTester<PasswordChangeRequest> jsonPasswordChangeRequest;

    @Autowired
    private JacksonTester<PasswordResetRequest> jsonPasswordResetRequest;

    @Test
     void register() throws Exception {
        // Given
        RegistrationRequest registrationRequest =
                new RegistrationRequest("user", "email@email.com", "password");
        GenericResponse registrationResponse = new GenericResponse(true, registrationRequest.getEmail());
        given(userService.register(registrationRequest)).willReturn(registrationResponse);

        // When
        MockHttpServletResponse response = mvc.perform(
                post("/api/v1/users/register").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRegistrationRequest.write(registrationRequest).getJson())
        ).andReturn().getResponse();

        // Then
        then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        then(response.getContentAsString()).isEqualTo(
                jsonGenericResponse.write(
                    registrationResponse
                ).getJson());
    }

    @Test
    @WithMockUser(authorities = "user:write")
    void roles() throws Exception{
        // Given
        Role role = new Role(RoleName.USER);
        Role role1 = new Role(RoleName.POST_MODERATOR);
        Role role2 = new Role(RoleName.COMMENT_MODERATOR);
        Role role3 = new Role(RoleName.SUPER_ADMIN);
        List<Role> roles = List.of(role, role1, role2, role3);

        // When
        MockHttpServletResponse response = mvc
                .perform(get("/api/v1/users/roles")).andReturn().getResponse();

        // Then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        //Todo ensure to test the json response
//        then(response.getContentAsString())
//                .isEqualTo(jsonRolesResponse.write(roles).getJson());
    }

    @Test
    @WithMockUser(authorities = "user:write")
    void giveRole() throws Exception{
        // Given
        String username = "user";
        ChangeRoleRequest changeRoleRequest = new ChangeRoleRequest();
        changeRoleRequest.setRole(RoleName.SUPER_ADMIN);
        GenericResponse roleChangeResponse = new GenericResponse(true,
                username + "'s role was successfully changed to " + changeRoleRequest.getRole());
        Authentication auth = Mockito.mock(Authentication.class);
        given(userService.changeRole(username, changeRoleRequest, auth)).willReturn(roleChangeResponse);

        // When
        MockHttpServletResponse response = mvc.perform(
                patch("/api/v1/users/user/role/change")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonChangeRoleRequest.write(changeRoleRequest).getJson())
        ).andReturn().getResponse();

        // Then
        then(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        then(response.getContentAsString())
                .isEqualTo(jsonGenericResponse.write(roleChangeResponse).getJson());
    }

    @Test
    @WithMockUser
    void changePassword() throws Exception {
        // Given
        Long id = 1L;
        PasswordChangeRequest passwordChangeRequest =
                new PasswordChangeRequest("old", "new", "new");
        GenericResponse passwordChangeResponse =
                new GenericResponse(true, "password successfully changed");
        Authentication auth = Mockito.mock(Authentication.class);
        given(userService.changePassword(id, passwordChangeRequest, auth)).willReturn(passwordChangeResponse);

        // When
        MockHttpServletResponse response = mvc.perform(
                patch("/api/v1/users/1/password/change").with(authentication(auth))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPasswordChangeRequest.write(passwordChangeRequest).getJson())
        ).andReturn().getResponse();

        // Then
        then(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        then(response.getContentAsString()).isEqualTo(jsonGenericResponse.write(passwordChangeResponse).getJson());
    }

    @Test
    void generatePasswordResetToken() throws Exception {
        // Given
        String email = "email@email.com";
        GenericResponse genericResponse =
                new GenericResponse(true, "please check your email for steps to reset your password");
        given(userService.generatePasswordResetToken(email)).willReturn(genericResponse);

        // When
        MockHttpServletResponse response = mvc.perform(
                post("/api/v1/users/" + email + "/password/reset")
        ).andReturn().getResponse();

        // Then
        then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        then(response.getContentAsString()).isEqualTo(jsonGenericResponse.write(genericResponse).getJson());
    }

    @Test
    void resetPassword() throws Exception {
        //Given
        String token = "tokennekottokennekot";
        GenericResponse genericResponse =
                new GenericResponse(true, "Please reset your password");
        given(userService.resetPassword(token)).willReturn(genericResponse);

        // When
        MockHttpServletResponse response = mvc.perform(
                get("/api/v1/users/password/reset?token=" + token)
        ).andReturn().getResponse();

        // Then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(jsonGenericResponse.write(genericResponse).getJson());
    }

    @Test
    void confirmPasswordReset() throws Exception {
        // Given
        String token = "tokennekottokennekot";
        PasswordResetRequest request = new PasswordResetRequest("password", "password");
        GenericResponse genericResponse =
                new GenericResponse(true, "password successfully changed");
        given(userService.confirmPasswordReset(request, token)).willReturn(genericResponse);

        // When
        MockHttpServletResponse response = mvc.perform(
                post("/api/v1/users/password/reset/confirm?token=" + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonPasswordResetRequest.write(request).getJson())
        ).andReturn().getResponse();

        // Then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(jsonGenericResponse.write(genericResponse).getJson());
    }
}