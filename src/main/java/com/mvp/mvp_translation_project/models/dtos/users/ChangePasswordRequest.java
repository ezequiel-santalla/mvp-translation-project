package com.mvp.mvp_translation_project.models.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(

        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Old Password is required")
        String oldPassword,

        @NotBlank(message = "New Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String newPassword) {

}
