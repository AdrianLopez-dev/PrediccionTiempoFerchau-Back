package com.ferchau.PrediccionTiempoFerchau_Back.application.service;

import com.ferchau.PrediccionTiempoFerchau_Back.domain.dto.PrediccionTiempoDto;

public interface PrediccionTiempoService {

    PrediccionTiempoDto getPrediccion(String codigoMunicipio, String unidadTemperatura);

}
