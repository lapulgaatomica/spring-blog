package com.project.blog.payloads;

import com.project.blog.entities.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode(exclude = {"comments"})
@AllArgsConstructor
@NoArgsConstructor
public class PostWithCommentsResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime created;
    private LocalDateTime edited;
    private List<Comment> comments;


}
