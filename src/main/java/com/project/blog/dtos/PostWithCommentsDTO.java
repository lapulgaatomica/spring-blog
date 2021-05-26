package com.project.blog.dtos;

import com.project.blog.entities.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode(exclude = {"comments"})
@AllArgsConstructor
@NoArgsConstructor
public class PostWithCommentsDTO {
    private Long id;

    private String title;
    private String content;
    private LocalDateTime dateCreated;
    private LocalDateTime dateEdited;
    private List<Comment> comments;
}
