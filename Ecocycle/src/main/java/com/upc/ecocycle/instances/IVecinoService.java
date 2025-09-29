package com.upc.ecocycle.instances;

import com.upc.ecocycle.dto.VecinoDTO;

import java.util.List;

public interface IVecinoService {
    String registrar(VecinoDTO vecinoDTO);
    String modificar(VecinoDTO vecinoDTO);
    String eliminar(Integer id);
    VecinoDTO buscarPorDni(String dni);
    VecinoDTO buscarPorId(Integer id);
    void actualizacionPuntos(Integer id);
    void calcularPuestos();
    List<VecinoDTO> listarVecinos();
    List<VecinoDTO> listarVecinosPorEvento(Integer idEvento);
    List<VecinoDTO> rankingFiltrado(String distrito, String genero, Integer edadMin, Integer edadMax);
}
