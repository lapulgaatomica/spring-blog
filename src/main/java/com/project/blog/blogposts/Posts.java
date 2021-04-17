package com.project.blog.blogposts;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "post body can't be empty")
    @NotNull(message = "please enter a post")
    private String post;
    private LocalDateTime dateCreated;
    private LocalDateTime dateEdited;

    public Posts(){

    }
}
