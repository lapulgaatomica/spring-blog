package com.project.blog.comments;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Value
public class CommentDTO {
    @NotBlank
    private String content;
    @Min(1)
    private Long postId;
}
