package com.ferchau.PrediccionTiempoFerchau_Back.application.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferchau.PrediccionTiempoFerchau_Back.application.configuration.ConfigurationPrediccionTiempoMunicipios;
import com.ferchau.PrediccionTiempoFerchau_Back.application.i18n.Constants;
import com.ferchau.PrediccionTiempoFerchau_Back.application.service.MunicipiosService;
import com.ferchau.PrediccionTiempoFerchau_Back.domain.dto.MunicipioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class MunicipiosServiceImpl implements MunicipiosService {

    @Autowired
    private ConfigurationPrediccionTiempoMunicipios configurationPrediccionTiempoMunicipios;

    @Autowired
    private ObjectMapper objectMapper;

    private static final int memorySize = 1024 * 1024 * 10;
    private WebClient webClient;

    @Autowired
    public MunicipiosServiceImpl (WebClient.Builder webClientBuilder, ConfigurationPrediccionTiempoMunicipios config) {
        this.configurationPrediccionTiempoMunicipios = config;
        this.webClient = webClientBuilder.baseUrl(config.getBaseurl())
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(memorySize))
                .defaultHeader("Authorization", "Bearer " + config.getToken()) // API Key en el header
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Override
    public List<MunicipioDto> getAllMunicipios() {
        // Recogemos la primera peticion para extraer la url definitiva de los datos
        String urlDatosMunicipios = this.webClient.get()
                .uri("/opendata/api/maestro/municipios")
                .retrieve()
                .bodyToMono(JsonNode.class) // Deserializa la respuesta a JsonNode
                .map(jsonNode -> jsonNode.path("datos").asText()).block();

        return this.webClient.get()
                .uri(Objects.requireNonNull(urlDatosMunicipios))
                .retrieve()
                .bodyToMono(String.class)
                .map(json -> {
                    try {
                        // Deserializar el JSON a una lista de mapas
                        List<Map<String, String>> data = objectMapper.readValue(json, new TypeReference<List<Map<String, String>>>() {});

                        // Crear una lista de objetos Municipio a partir de los resultados
                        List<MunicipioDto> municipios = new ArrayList<>();
                        for (Map<String, String> entry : data) {
                            municipios.add(new MunicipioDto(
                                    entry.get(Constants.PROPIEDAD_MUNICIPIO_NOMBRE),
                                    entry.get(Constants.PROPIEDAD_MUNICIPIO_CODIGO)
                            ));
                        }
                        return municipios;
                    } catch (IOException e) {
                        throw new RuntimeException("Error deserializando el JSON", e);
                    }
                }).block();
    }
}
