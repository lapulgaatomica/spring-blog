package com.project.blog.blogposts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "post body can't be empty")
    @NotNull(message = "please enter a post")
    private String content;

    private LocalDateTime dateCreated;
    private LocalDateTime dateEdited;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Set<Comment> comment;


    public Post(String content){
        this.content = content;
    }

    public Post(long id, String content, LocalDateTime dateCreated, LocalDateTime dateEdited) {
        this.id = id;
        this.content = content;
        this.dateCreated = dateCreated;
        this.dateEdited = dateEdited;
    }
}
