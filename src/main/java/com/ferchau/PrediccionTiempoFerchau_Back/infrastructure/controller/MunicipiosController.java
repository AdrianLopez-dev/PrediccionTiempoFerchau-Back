package com.ferchau.PrediccionTiempoFerchau_Back.infrastructure.controller;


import com.ferchau.PrediccionTiempoFerchau_Back.application.service.MunicipiosService;
import com.ferchau.PrediccionTiempoFerchau_Back.domain.dto.MunicipioDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
@Tag(name = "Municipios", description = "Operaciones relacionadas con los municipios")
public class MunicipiosController {

    @Autowired
    private MunicipiosService municipiosService;

    @Operation(summary = "Obtener todos los municipios", description = "Devuelve una lista de todos los municipios disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MunicipioDto.class))),
            @ApiResponse(responseCode = "500", description = "Error en el servidor",
                    content = @Content)
    })
    @GetMapping("/municipios")
    public List<MunicipioDto> getAllMunicipios() {
        return municipiosService.getAllMunicipios();
    }
}