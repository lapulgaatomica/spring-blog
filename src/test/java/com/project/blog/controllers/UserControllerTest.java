package com.project.blog.controllers;

import com.project.blog.entities.BlogUser;
import com.project.blog.payloads.GenericResponse;
import com.project.blog.payloads.RegistrationRequest;
import com.project.blog.security.JwtConfigProperties;
import com.project.blog.services.UserDetailsServiceImpl;
import com.project.blog.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
    private JacksonTester<GenericResponse> jsonRegistrationResponse;

    @Test
    public void register() throws Exception {
        // Given
        RegistrationRequest registrationRequest =
                new RegistrationRequest("usergaga", "emailgaga@email.com", "password");
        GenericResponse registrationResponse = new GenericResponse(true, registrationRequest.getEmail());
        given(userService
                .register(registrationRequest))
                .willReturn(registrationResponse);


        // When
        MockHttpServletResponse response = mvc.perform(
                post("/api/v1/users/register").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRegistrationRequest.write(registrationRequest).getJson())
        ).andReturn().getResponse();

        // Then
        then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        then(response.getContentAsString()).isEqualTo(
                jsonRegistrationResponse.write(
                    registrationResponse
                ).getJson());
    }

    @Test
    void roles() {
    }

    @Test
    void giveRole() {
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