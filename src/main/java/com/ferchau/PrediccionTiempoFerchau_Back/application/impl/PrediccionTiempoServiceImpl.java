package com.ferchau.PrediccionTiempoFerchau_Back.application.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferchau.PrediccionTiempoFerchau_Back.application.configuration.ConfigurationPrediccionTiempoMunicipios;
import com.ferchau.PrediccionTiempoFerchau_Back.application.i18n.Constants;
import com.ferchau.PrediccionTiempoFerchau_Back.application.service.PrediccionTiempoService;
import com.ferchau.PrediccionTiempoFerchau_Back.domain.dto.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class PrediccionTiempoServiceImpl implements PrediccionTiempoService {

    private final WebClient webClient;

    @Autowired
    private ConfigurationPrediccionTiempoMunicipios configurationPrediccionTiempoMunicipios;

    @Autowired
    private ObjectMapper objectMapper;

    private static final int memorySize = 1024 * 1024 * 10;

    private final String fechaManana = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "T00:00:00";

    public PrediccionTiempoServiceImpl(WebClient.Builder webClientBuilder, ConfigurationPrediccionTiempoMunicipios config) {
        this.configurationPrediccionTiempoMunicipios = config;
        this.webClient = webClientBuilder.baseUrl(config.getBaseurl())
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(memorySize))
                .defaultHeader("Authorization", "Bearer " + config.getToken())
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Override
    public PrediccionTiempoDto getPrediccion(String codigoMunicipio, String unidadTemperatura) {
        // Recogemos la primera peticion para extraer la url definitiva de los datos
        String urlDatosMunicipios = this.webClient.get()
                .uri(this.configurationPrediccionTiempoMunicipios.getUrlPrediccioTiempo() + codigoMunicipio)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> jsonNode.path("datos").asText()).block();
        // Llamada a AEMET para obtener todas las predicciones por Municipio de toda la semana
        String response = this.webClient.get()
                .uri(Objects.requireNonNull(urlDatosMunicipios))
                .retrieve()
                .bodyToMono(String.class).block();

        try {
            // Recogemos toda la informacion de la prediccion del dia de mañana
            List<PrediccionAEMETDto> prediccionesAEMET = objectMapper.readValue(response, new TypeReference<>() {
            });
            PrediccionAEMETDto prediccionAEMET = prediccionesAEMET.getFirst();
            PrediccionDto prediccionDto = prediccionAEMET.getPrediccion();
            List<DiaDto> listDias = prediccionDto.getDia();

            // Comprobamos en la lista de dias, cual es dia que corresponde con el dia de mañana para extraer la información necesaria
            for (DiaDto dia : listDias) {
                if (dia.getFecha().equals(fechaManana)) {
                    // Creamos el objeto final para la respuesta
                    PrediccionTiempoDto resultadoFinal = new PrediccionTiempoDto();
                    resultadoFinal.setMediaTemperatura(unidadTemperatura.equals(Constants.UNIDAD_MEDIDA_TEMPERATURA_FARENHEIT) ?
                            dia.getTemperatura().getFarenheit() : dia.getTemperatura().getCelsius());
                    resultadoFinal.setUnidadTemperatura(unidadTemperatura);
                    resultadoFinal.setProbPrecipitacion(dia.getProbPrecipitacion());

                    return resultadoFinal;
                }
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
