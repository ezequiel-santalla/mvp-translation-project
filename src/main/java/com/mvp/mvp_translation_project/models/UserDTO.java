package com.mvp.mvp_translation_project.models;

import com.mvp.mvp_translation_project.types.RoleType;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserDTO {

    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String identityNumber;
    private String email;
    private String cellphone;
}