package com.upc.ecocycle.instances;

import com.upc.ecocycle.dto.VecinoDTO;

import java.util.List;

public interface IVecinoService {
    VecinoDTO registrar(VecinoDTO vecinoDTO);
    VecinoDTO modificar(VecinoDTO vecinoDTO);
    String eliminar(Integer id);
    VecinoDTO buscarPorDni(String dni);
    VecinoDTO buscarPorId(Integer id);
    void actualizacionPuntos(Integer id);
    void calcularPuestos();
    List<VecinoDTO> rankingFiltrado(String distrito, String genero, Integer edadMin, Integer edadMax);
}
