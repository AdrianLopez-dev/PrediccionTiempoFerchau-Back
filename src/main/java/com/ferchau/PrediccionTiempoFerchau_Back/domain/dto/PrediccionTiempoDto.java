package com.ferchau.PrediccionTiempoFerchau_Back.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Validated
public class PrediccionTiempoDto {

    private double mediaTemperatura;

    private String unidadTemperatura;

    private List<ProbPrecipitacionDto> probPrecipitacion;
}
