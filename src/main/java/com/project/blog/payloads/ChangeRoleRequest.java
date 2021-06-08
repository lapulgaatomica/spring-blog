package com.project.blog.payloads;

import com.project.blog.entities.enums.RoleName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeRoleRequest {
    private RoleName role;
}
