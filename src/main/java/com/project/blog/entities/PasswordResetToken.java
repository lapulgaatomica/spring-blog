package com.project.blog.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

    @OneToOne
    private BlogUser user;
    private LocalDateTime expiresAt;

    public PasswordResetToken(String token, BlogUser user, LocalDateTime expiresAt){
        this.token = token;
        this.user = user;
        this.expiresAt = expiresAt;
    }
}
