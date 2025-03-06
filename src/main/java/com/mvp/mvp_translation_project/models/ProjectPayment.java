package com.mvp.mvp_translation_project.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FlatFeePayment.class, name = "FlatFee"),
        @JsonSubTypes.Type(value = RateBasedPayment.class, name = "RateBased")
})

@NoArgsConstructor
@AllArgsConstructor

@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 👈 Se define aquí la estrategia
@SuperBuilder
@Getter @Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "payment_details")
public abstract class ProjectPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
