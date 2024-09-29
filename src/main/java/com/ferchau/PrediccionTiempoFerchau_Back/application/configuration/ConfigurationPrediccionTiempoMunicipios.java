package com.ferchau.PrediccionTiempoFerchau_Back.application.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "prediccion.tiempo.municipios")
public class ConfigurationPrediccionTiempoMunicipios {

    private String token;

    private String baseurl;

    private String urlMunicipios;

    private String urlPrediccioTiempo;
}
