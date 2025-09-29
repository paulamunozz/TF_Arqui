package com.upc.ecocycle.instances;

import com.upc.ecocycle.dto.MunicipalidadDTO;

import java.util.List;

public interface IMunicipalidadService {
    void actualizacionPuntos(String distrito);
    void calcularPuestos();
    MunicipalidadDTO buscarPorId(Integer id);
    MunicipalidadDTO buscarPorCodigo(String codigo);
    MunicipalidadDTO buscarPorDistrito(String distrito);
    List<MunicipalidadDTO> listarMunicipalidades();
    List<MunicipalidadDTO> rankingMunicipalidades();
}
