package com.upc.ecocycle.instances;

import com.upc.ecocycle.dto.EventoDTO;
import java.time.LocalDate;
import java.util.List;

public interface IEventoService {
    String registrar(EventoDTO eventoDTO);
    String modificar(EventoDTO eventoDTO);
    String eliminar(Integer id);
    EventoDTO buscarPorId(Integer id);
    void actualizarPesoActual();
    List<EventoDTO> buscarPorNombre(String nombre);
    List<EventoDTO> listarEventos(String distrito, String nombre, String tipo, String metodo, LocalDate fechaInicio, LocalDate fechaFin);
    List<EventoDTO> listarEventosPorVecino(Integer idVecino, String nombre, String tipo, String metodo, LocalDate fechaInicio, LocalDate fechaFin);
}
