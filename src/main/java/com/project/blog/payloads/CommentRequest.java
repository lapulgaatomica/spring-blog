package com.project.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

//@Value
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    @NotBlank
    private String content;
}
