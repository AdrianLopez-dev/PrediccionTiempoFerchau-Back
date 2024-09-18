package com.ferchau.PrediccionTiempoFerchau_Back.infrastructure.controller;


import com.ferchau.PrediccionTiempoFerchau_Back.application.service.MunicipiosService;
import com.ferchau.PrediccionTiempoFerchau_Back.domain.dto.MunicipioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/api")
public class MunicipiosController {

    @Autowired
    private MunicipiosService municipiosService;

    @GetMapping("/municipios")
    public List<MunicipioDto> getAllMunicipios() {
        return municipiosService.getAllMunicipios();
    }
}