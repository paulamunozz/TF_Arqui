package com.upc.ecocycle.instances;

import com.upc.ecocycle.dto.MunicipalidadDTO;

public interface IMunicipalidadService {
    String registrar(MunicipalidadDTO municipalidadDTO);
    String modificar(MunicipalidadDTO municipalidadDTO);
    String eliminar(Integer idMunicipalidad);
    MunicipalidadDTO buscarPorId(Integer idMunicipalidad);

}
