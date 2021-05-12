package com.project.blog.comments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.blog.posts.Post;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private LocalDateTime dateCreated;
    private LocalDateTime dateEdited;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    public Comment(Long id, String content, Post post) {
        this.id = id;
        this.content = content;
        this.post = post;
    }
}
