package com.project.blog.payloads;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonPropertyOrder({"success", "message"})
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse {
    private Boolean success;
    private String message;
}
