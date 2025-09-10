package com.upc.ecocycle.instances;

import com.upc.ecocycle.dto.EventoDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IEventoService {
    String registrar(EventoDTO eventoDTO);
    String modificar(EventoDTO eventoDTO);
    String eliminar(Integer idEvento);
    EventoDTO buscarPorId(Integer idEvento);
    List<EventoDTO> listarEventos(String nombre, String distrito, String estado, LocalDate fechaInicio, LocalDate fechaFin);
}
