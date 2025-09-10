package com.upc.ecocycle.instances;

import com.upc.ecocycle.dto.MunicipalidadDTO;

import java.util.List;

public interface IMunicipalidadService {
    String actualizacionPuntos(Integer idUsuario, Integer puntos);
    void calcularPuestos();
    MunicipalidadDTO buscarPorId(Integer idUsuario);
    MunicipalidadDTO buscarPorCodigo(String codigoUsuario);
    MunicipalidadDTO buscarPorDistrito(String distrito);
    List<MunicipalidadDTO> listarMunicipalidades();
    List<MunicipalidadDTO> rankingMunicipalidades();
}
