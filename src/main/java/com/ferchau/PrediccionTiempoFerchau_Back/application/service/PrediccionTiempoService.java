package com.ferchau.PrediccionTiempoFerchau_Back.application.service;

import com.ferchau.PrediccionTiempoFerchau_Back.domain.dto.MunicipioDto;
import com.ferchau.PrediccionTiempoFerchau_Back.domain.dto.PrediccionTiempoDto;

import java.util.List;

public interface PrediccionTiempoService {

    PrediccionTiempoDto getPrediccion(String codigoMunicipio, String unidadTemperatura);

}
