package com.atm.buenas_practicas_java.DTO;

import com.atm.buenas_practicas_java.entities.Concert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalePointsDTO {

    private Long id;
    private String salePointUrl;
    private Concert concert;
    private Double ticketPrice;

}
