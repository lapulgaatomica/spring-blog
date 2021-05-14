package com.project.blog.dtos;

import lombok.Value;

import javax.validation.constraints.NotBlank;

//@Data
//@NoArgsConstructor
@Value
public class PostDTO {
    @NotBlank
    private String title, content ;
}
