package com.mvp.mvp_translation_project.models;

import com.mvp.mvp_translation_project.types.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserUpdateDTO {
    private String name;
    private String lastName;
    private String email;
    private String cellphone;
    private Address address;
}