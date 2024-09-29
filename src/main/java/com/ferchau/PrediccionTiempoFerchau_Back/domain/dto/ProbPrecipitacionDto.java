package com.ferchau.PrediccionTiempoFerchau_Back.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Validated
public class ProbPrecipitacionDto {

    private int value;

    private String periodo;
}
