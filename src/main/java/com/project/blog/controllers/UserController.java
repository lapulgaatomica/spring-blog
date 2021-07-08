package com.project.blog.controllers;

import com.project.blog.payloads.*;
import com.project.blog.entities.Role;
import com.project.blog.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<GenericResponse> register(@RequestBody @Valid RegistrationRequest registrationRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(registrationRequest));
    }

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<List<Role>> roles(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getRoles());
    }

    @PatchMapping("/{username}/role/change")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<String> giveRole(@PathVariable("username") String username,
                                           @RequestBody ChangeRoleRequest changeRoleRequest){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.changeRole(username, changeRoleRequest));
    }

    @PatchMapping("/{id}/password/change")
    public ResponseEntity<GenericResponse> changePassword(@PathVariable("id") Long id, @RequestBody PasswordChangeRequest request){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.changePassword(id, request));
    }

    @PostMapping("/{email}/password/reset")
    public ResponseEntity<GenericResponse> generatePasswordResetToken(@PathVariable("email") String email){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.generatePasswordResetToken(email));
    }

    @GetMapping("/password/reset")
    public ResponseEntity<GenericResponse> resetPassword(@RequestParam("token") String token){
        return ResponseEntity.status(HttpStatus.OK).body(userService.resetPassword(token));
    }

    @PostMapping("/password/reset/confirm")
    public ResponseEntity<GenericResponse> confirmPasswordReset(@RequestBody PasswordResetRequest request,
                                                                @RequestParam("token") String token){
        return ResponseEntity.status(HttpStatus.OK).body(userService.confirmPasswordReset(request, token));
    }
}
