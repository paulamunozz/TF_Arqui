package com.upc.ecocycle.instances;

import com.upc.ecocycle.dto.VecinoDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IVecinoService {
    String registrar(VecinoDTO vecinoDTO);
    String modificar(VecinoDTO vecinoDTO);
    String eliminar(Integer idUsuario);
    VecinoDTO buscarPorCodigo(String codigoUsuario);
    VecinoDTO buscarPorId(Integer idUsuario);
    void actualizacionPuntos(Integer idVecino);
    void calcularPuestos();
    List<VecinoDTO> listarVecinos();
    List<VecinoDTO> rankingFiltrado(String distrito, String genero, Integer edadMin, Integer edadMax);
}
