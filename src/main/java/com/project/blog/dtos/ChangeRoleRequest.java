package com.project.blog.dtos;

import com.project.blog.entities.enums.RoleName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeRoleRequest {

    private RoleName roleName;

}
