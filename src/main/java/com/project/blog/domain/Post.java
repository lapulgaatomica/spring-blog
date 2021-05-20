package com.project.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"comments"})
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private LocalDateTime dateCreated;
    private LocalDateTime dateEdited;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "post")
    private Set<Comment> comments;

    public Post(Long id, String title, String content, LocalDateTime dateCreated, LocalDateTime dateEdited) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.dateCreated = dateCreated;
        this.dateEdited = dateEdited;
    }
}
