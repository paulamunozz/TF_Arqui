package com.upc.ecocycle.instances;

import com.upc.ecocycle.dto.CantidadReciclajeDTO;
import com.upc.ecocycle.dto.ReciclajeDTO;

import java.time.LocalDate;
import java.util.List;

public interface IReciclajeService {
    ReciclajeDTO registrar(ReciclajeDTO reciclajeDTO);
    ReciclajeDTO modificar(ReciclajeDTO reciclajeDTO);
    String eliminar(Integer id);
    ReciclajeDTO buscarPorId(Integer id);
    List<ReciclajeDTO> listarReciclajePorVecino(
            Integer vecinoId, String tipo, String metodo,
            LocalDate fechaInicio, LocalDate fechaFin);
    List<ReciclajeDTO> listarReciclajeFiltrado(
            String distrito, String tipo, String metodo, LocalDate fechaInicio,
            LocalDate fechaFin, String genero, Integer edadMin, Integer edadMax);
    List<CantidadReciclajeDTO> listarCantidadReciclaje(String distrito);
}
