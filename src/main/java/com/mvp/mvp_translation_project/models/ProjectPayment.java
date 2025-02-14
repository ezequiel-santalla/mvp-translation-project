package com.mvp.mvp_translation_project.models;

import com.mvp.mvp_translation_project.types.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "payment_details")
public class ProjectPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    @Column(precision = 10, scale = 2)
    private BigDecimal flatFee;

    @Column(precision = 10, scale = 2)
    private BigDecimal rate;

    @Column
    private Integer quantity;
}
