package com.ferchau.PrediccionTiempoFerchau_Back.application.service;


import com.ferchau.PrediccionTiempoFerchau_Back.domain.dto.MunicipioDto;

import java.util.List;

/**
 * Servicio para la entidad Municipios de AEMET
 * @author alopdez
 */
public interface MunicipiosService {

    List<MunicipioDto> getAllMunicipios();

}
