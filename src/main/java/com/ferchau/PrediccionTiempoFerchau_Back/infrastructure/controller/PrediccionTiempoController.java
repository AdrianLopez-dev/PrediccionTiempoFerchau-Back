package com.ferchau.PrediccionTiempoFerchau_Back.infrastructure.controller;

import com.ferchau.PrediccionTiempoFerchau_Back.application.service.PrediccionTiempoService;
import com.ferchau.PrediccionTiempoFerchau_Back.domain.dto.PrediccionTiempoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Predicción del Tiempo", description = "Operaciones relacionadas con la predicción del tiempo")
public class PrediccionTiempoController {

    @Autowired
    private PrediccionTiempoService prediccionTiempoService;

    @Operation(summary = "Obtener predicción meteorológica",
            description = "Devuelve la predicción meteorológica para un municipio dado basado en su código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Predicción obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PrediccionTiempoDto.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error en el servidor",
                    content = @Content)
    })
    @GetMapping("/obtenerPrediccion")
    public PrediccionTiempoDto getPrediccion(@Parameter(description = "Código del municipio para obtener la predicción", example = "44004")
                                             @RequestParam String codigoMunicipio,
                                             @Parameter(description = "Unidad de temperatura (por defecto en grados Celsius)", example = "G_FAH", required = false)
                                             @RequestParam(defaultValue = "G_CEL") String unidadTemperatura) {
        return prediccionTiempoService.getPrediccion(codigoMunicipio, unidadTemperatura);
    }
}

