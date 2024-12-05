package com.mvp.mvp_translation_project.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserRegistrationDTO {
    private String name;
    private String lastName;
    private String email;
    private String password;
}