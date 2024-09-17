package com.ferchau.PrediccionTiempoFerchau_Back.application.configuration;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@ConfigurationProperties(prefix = "prediccion.tiempo.municipios")
public class ConfigurationPrediccionTiempoMunicipios {

    private String token;

    private String baseurl;
}
