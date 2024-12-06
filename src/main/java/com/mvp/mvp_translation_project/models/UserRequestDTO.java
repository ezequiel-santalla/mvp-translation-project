package com.mvp.mvp_translation_project.models;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    //@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{6,}$",
    //        message = "Password must be at least 6 characters long and include at least one number, one uppercase letter, one lowercase letter, and one special character")
    private String password;

    @NotNull(message = "Birth Date is required")
    @Past(message = "Birth Date must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Identity Number is required")
    private String identityNumber;

    @NotBlank(message = "Cellphone is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Cellphone must be a valid phone number with 10 to 15 digits")
    private String cellphone;
}