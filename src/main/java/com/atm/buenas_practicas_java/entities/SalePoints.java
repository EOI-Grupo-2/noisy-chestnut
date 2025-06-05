package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalePoints {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String salePointUrl;

    @Column(nullable = false)
    private DecimalFormat ticketPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "concert_id", nullable = false)
    private Concert concert;
}
