package com.mvp.mvp_translation_project.models;

import com.mvp.mvp_translation_project.types.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserResponseDTO {
    private String name;
    private String lastName;
    private String email;
    private RoleType role;
}