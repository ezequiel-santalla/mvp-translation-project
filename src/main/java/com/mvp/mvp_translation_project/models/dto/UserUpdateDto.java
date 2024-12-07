package com.mvp.mvp_translation_project.models.dto;

import com.mvp.mvp_translation_project.models.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserUpdateDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Birth Date is required")
    @Past(message = "Birth Date must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Identity Number is required")
    private String identityNumber;

    @NotBlank(message = "Cellphone is required")
    @Pattern(regexp = "^\\+?\\d{10,15}$", message = "Cellphone must be a valid phone number with 10 to 15 digits")    private String cellphone;

    @Valid
    private Address address;
}