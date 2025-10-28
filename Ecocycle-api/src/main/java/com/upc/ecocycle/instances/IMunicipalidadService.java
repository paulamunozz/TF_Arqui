package com.upc.ecocycle.instances;

import com.upc.ecocycle.dto.MunicipalidadDTO;

import java.util.List;

public interface IMunicipalidadService {
    String modificarContrasena(Integer id, String contrasena);
    void actualizacionPuntos(String distrito);
    void calcularPuestos();
    MunicipalidadDTO buscarPorId(Integer id);
    MunicipalidadDTO buscarPorCodigo(String codigo);
    List<MunicipalidadDTO> listarMunicipalidades();
    List<MunicipalidadDTO> rankingMunicipalidades();
}
