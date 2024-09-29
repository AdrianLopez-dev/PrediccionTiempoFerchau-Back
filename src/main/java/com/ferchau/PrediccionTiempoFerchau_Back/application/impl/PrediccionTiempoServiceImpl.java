package com.ferchau.PrediccionTiempoFerchau_Back.application.impl;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferchau.PrediccionTiempoFerchau_Back.application.configuration.ConfigurationPrediccionTiempoMunicipios;
import com.ferchau.PrediccionTiempoFerchau_Back.application.i18n.Constants;
import com.ferchau.PrediccionTiempoFerchau_Back.application.service.PrediccionTiempoService;
import com.ferchau.PrediccionTiempoFerchau_Back.domain.dto.PrediccionTiempoDto;
import com.ferchau.PrediccionTiempoFerchau_Back.domain.dto.ProbPrecipitacionDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        String urlDatosMunicipios = this.webClient.get()
                .uri(this.configurationPrediccionTiempoMunicipios.getUrlPrediccioTiempo() + codigoMunicipio)
                .retrieve()
                .bodyToMono(JsonNode.class) // Deserializa la respuesta a JsonNode
                .map(jsonNode -> jsonNode.path("datos").asText()).block();

         String response = this.webClient.get()
                .uri(Objects.requireNonNull(urlDatosMunicipios))
                .retrieve()
                .bodyToMono(String.class).block();

        System.out.println(response);
        System.out.println(urlDatosMunicipios);

        // Parseamos la respuesta JSON
        try {
            JSONArray responseArray = new JSONArray(response);

            // Accedemos al primer objeto del array, asumiendo que siempre hay al menos uno
            JSONObject responseObject = responseArray.getJSONObject(0);

            // Accedemos al objeto "prediccion"
            JSONObject prediccion = responseObject.getJSONObject("prediccion");

            // Accedemos al array "dia"
            JSONArray dias = prediccion.getJSONArray("dia");

            // Fecha de mañana en el formato adecuado
            String fechaManana = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "T00:00:00";

            for (int i = 0; i < dias.length(); i++) {
                JSONObject dia = dias.getJSONObject(i);
                String fecha = dia.getString("fecha"); // Usamos la fecha completa

                if (fecha.equals(fechaManana)) {
                    // Procesamos la temperatura
                    JSONObject temperatura = dia.getJSONObject("temperatura");
                    double mediaTemperatura = (temperatura.getDouble("maxima") + temperatura.getDouble("minima")) / 2;

                    if(unidadTemperatura.equals(Constants.UNIDAD_MEDIDA_TEMPERATURA_FARENHEIT)){
                        // Convertimos la temperatura a Fahrenheit
                        mediaTemperatura = (mediaTemperatura * 9/5) + 32;
                    }

                    // Procesamos probabilidad de precipitación
                    JSONArray probPrecipitacion = dia.getJSONArray("probPrecipitacion");
                    List<ProbPrecipitacionDto> probPrecipitaciones = new ArrayList<>();

                    for (int j = 0; j < probPrecipitacion.length(); j++) {
                        JSONObject prob = probPrecipitacion.getJSONObject(j);
                        String periodo = prob.getString("periodo");

                        ProbPrecipitacionDto probNueva = new ProbPrecipitacionDto();
                        probNueva.setProbabilidad(prob.getInt("value"));
                        probNueva.setPeriodo(periodo);
                        probPrecipitaciones.add(probNueva);

                    }

                    // Creamos el objeto final
                    PrediccionTiempoDto resultadoFinal = new PrediccionTiempoDto();
                    resultadoFinal.setMediaTemperatura(mediaTemperatura);
                    resultadoFinal.setUnidadTemperatura(unidadTemperatura);
                    resultadoFinal.setProbPrecipitacion(probPrecipitaciones);

                    return resultadoFinal;
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
