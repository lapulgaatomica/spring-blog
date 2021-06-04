package com.project.blog.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

//@Value
@Data
@NoArgsConstructor
public class CommentDTO {
    @NotBlank
    private String content;
//    @Min(1)
//    private Long postId;
}
