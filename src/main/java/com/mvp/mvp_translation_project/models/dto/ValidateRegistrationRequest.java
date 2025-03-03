package com.mvp.mvp_translation_project.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ValidateRegistrationRequest(

        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Pre-registration Code is required")
        String registrationCode) {

}
