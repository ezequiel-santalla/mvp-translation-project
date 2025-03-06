package com.mvp.mvp_translation_project.models;

import com.mvp.mvp_translation_project.types.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RateBasedPayment extends ProjectPayment {
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(precision = 10, scale = 2)
    private BigDecimal rate; // Tarifa (por palabra, hora, minuto, página, etc.)

    private Integer quantity; // Cantidad (número de palabras, minutos, horas, páginas, etc.)
}
