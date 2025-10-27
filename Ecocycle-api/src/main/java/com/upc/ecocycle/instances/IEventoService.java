package com.upc.ecocycle.instances;

import com.upc.ecocycle.dto.EventoDTO;
import com.upc.ecocycle.dto.funcionalidades.CantidadEventosLogradosDTO;

import java.time.LocalDate;
import java.util.List;

public interface IEventoService {
    EventoDTO registrar(EventoDTO eventoDTO);
    EventoDTO modificar(EventoDTO eventoDTO);
    String eliminar(Integer id);
    EventoDTO buscarPorId(Integer id);
    void actualizarPesoActual();
    List<EventoDTO> listarEventos(String distrito, String nombre, String tipo, String metodo, LocalDate fechaInicio, LocalDate fechaFin);
    List<EventoDTO> listarEventosPorVecino(Integer idVecino, String nombre, String tipo, String metodo, LocalDate fechaInicio, LocalDate fechaFin);
    CantidadEventosLogradosDTO cantidadEventosLogrados(String distrito);
}
