package com.ferchau.PrediccionTiempoFerchau_Back.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Validated
public class TemperaturaDto {
    private int maxima;
    private int minima;
    private List<TemperaturaDatoDto> dato;
}
