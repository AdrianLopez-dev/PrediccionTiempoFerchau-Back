package com.ferchau.PrediccionTiempoFerchau_Back.infrastructure.controller;

import com.ferchau.PrediccionTiempoFerchau_Back.application.service.PrediccionTiempoService;
import com.ferchau.PrediccionTiempoFerchau_Back.domain.dto.PrediccionTiempoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PrediccionTiempoController {

    @Autowired
    private PrediccionTiempoService prediccionTiempoService;

    @GetMapping("/obtenerPrediccion")
    public PrediccionTiempoDto getPrediccion(@RequestParam String codigoMunicipio,
                                             @RequestParam(defaultValue = "G_CEL") String unidadTemperatura)
    {
        return prediccionTiempoService.getPrediccion(codigoMunicipio,unidadTemperatura);
    }
}

