package com.mvp.mvp_translation_project.models;

import jakarta.persistence.Column;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlatFeePayment extends ProjectPayment{
    @Column(precision = 10, scale = 2)
    private BigDecimal cost;
}
