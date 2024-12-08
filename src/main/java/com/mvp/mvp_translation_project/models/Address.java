package com.mvp.mvp_translation_project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Country is required")
    @Size(max = 50, message = "Country must not exceed 50 characters")
    private String country;

    @NotBlank(message = "Province is required")
    @Size(max = 50, message = "Province must not exceed 50 characters")
    private String province;

    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City must not exceed 50 characters")
    private String city;

    @NotBlank(message = "Street is required")
    @Size(max = 50, message = "Street must not exceed 100 characters")
    private String street;

    @NotBlank(message = "Number is required")
    @Size(max = 10, message = "Number must not exceed 10 characters")
    private String number;

    @NotBlank(message = "Zip Code is required")
    @Pattern(regexp = "^\\d{4,5}(\\d{4})?$",
            message = "Zip Code must be in the format 1234, 12345, or 12345-6789")
    private String zipCode;

    @PastOrPresent(message = "Creation Date must be in the past or present")
    @Column(nullable = false)
    private LocalDateTime creationDate;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now(); // Establece la fecha actual al persistir
    }
}
