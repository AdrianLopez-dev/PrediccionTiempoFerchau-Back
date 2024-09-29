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
public class PrediccionAEMETDto {
    private OrigenDto origen;
    private String elaborado;
    private String nombre;
    private String provincia;
    private List<DiaDto> prediccion;
    private int id;
    private double version;
}