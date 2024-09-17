package com.ferchau.PrediccionTiempoFerchau_Back.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Validated
public class ProbPrecipitacionDto {

    private int probabilidad;

    private String periodo;
}
