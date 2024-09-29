package com.ferchau.PrediccionTiempoFerchau_Back.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Validated
public class OrigenDto {
    private String productor;
    private String web;
    private String enlace;
    private String language;
    private String copyright;
    private String notaLegal;
}