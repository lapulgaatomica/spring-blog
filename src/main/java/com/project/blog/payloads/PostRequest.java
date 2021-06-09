package com.project.blog.payloads;

import lombok.Value;

import javax.validation.constraints.NotBlank;

//@Data
//@NoArgsConstructor
@Value
public class PostRequest {
    @NotBlank
    private String title, content ;
}
