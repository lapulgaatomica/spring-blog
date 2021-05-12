package com.project.blog.posts;

import lombok.Value;

import javax.validation.constraints.NotBlank;

//@Data
//@NoArgsConstructor
@Value
public class PostDTO {
    @NotBlank
    private String title, content ;
}
