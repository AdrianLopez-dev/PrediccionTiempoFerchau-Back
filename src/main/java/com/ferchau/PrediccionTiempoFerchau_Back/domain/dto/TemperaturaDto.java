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
    private double maxima;
    private double minima;
    private List<TemperaturaDatoDto> dato;

    public double getCelsius() {
        return (this.maxima + this.minima) / 2;
    }

    public double getFarenheit() {
        return (((this.maxima + this.minima) / 2) * 9 / 5) + 32;
    }
}
