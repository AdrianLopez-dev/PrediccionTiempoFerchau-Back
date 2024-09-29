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
public class SensTermicaDto {
    private int maxima;
    private int minima;
    private List<SensTermicaDatoDto> dato;
}