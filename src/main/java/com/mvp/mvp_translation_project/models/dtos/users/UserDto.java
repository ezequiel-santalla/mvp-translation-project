package com.mvp.mvp_translation_project.models.dtos.users;

import com.mvp.mvp_translation_project.types.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserDto {
    private Long id;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String identityNumber;
    private String email;
    private String cellphone;
    private RoleType role;
}