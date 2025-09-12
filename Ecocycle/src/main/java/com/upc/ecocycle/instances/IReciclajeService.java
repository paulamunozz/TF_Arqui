package com.upc.ecocycle.instances;

import com.upc.ecocycle.dto.CantidadReciclajeDTO;
import com.upc.ecocycle.dto.ReciclajeDTO;

import java.time.LocalDate;
import java.util.List;

public interface IReciclajeService {
    String registrar(ReciclajeDTO reciclajeDTO);
    String modificar(ReciclajeDTO reciclajeDTO);
    String eliminar(Integer idReciclaje);
    List<ReciclajeDTO> listarReciclajeVecino(Integer vecinoId);
    List<ReciclajeDTO> listarReciclajeDistrito(String distrito, String tipo, String metodo, LocalDate fechaInicio, LocalDate fechaFin, String genero, Integer edadMin, Integer edadMax);
    List<CantidadReciclajeDTO> listarCantidadReciclaje();
}
