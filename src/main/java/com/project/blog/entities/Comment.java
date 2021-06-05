package com.project.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"post"})
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private LocalDateTime dateCreated;
    private LocalDateTime dateEdited;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.REFRESH, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @JsonIgnore
    @ManyToOne
    public BlogUser creator;

    public Comment(Long id, String content, Post post, BlogUser creator) {
        this.id = id;
        this.content = content;
        this.post = post;
        this.creator = creator;
    }
}
