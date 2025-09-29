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
    List<EventoDTO> listarEventos(String nombre, String tipo, String distrito, String estado, LocalDate fechaInicio, LocalDate fechaFin);
    List<EventoDTO> listarEventosPorVecino(Integer idVecino);
}
