package com.project.blog.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

//@Value
@Data
@NoArgsConstructor
public class CommentDTO {
    @NotBlank
    private String content;
}
