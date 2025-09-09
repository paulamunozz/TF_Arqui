package com.upc.ecocycle.instances;

import com.upc.ecocycle.dto.VecinoDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IVecinoService {
    public String registrar(VecinoDTO vecinoDTO);
    public String modificar(VecinoDTO vecinoDTO);
    public String eliminar(Integer idVecino);
    public VecinoDTO buscarPorCodigo(String codigoUsuario);
    public String actualizacionPuntos(Integer idVecino, Integer puntos);
    public void calcularPuestos();
    public List<VecinoDTO> listarVecinos();
    public List<VecinoDTO> rankingFiltrado(String distrito, String genero, Integer edadMin, Integer edadMax);
}
