package com.upc.ecocycle.instances;

import com.upc.ecocycle.dto.funcionalidades.CantidadesVecinosPorEvento;
import com.upc.ecocycle.dto.funcionalidades.ComentariosEventoDTO;
import com.upc.ecocycle.dto.EventoXVecinoDTO;

import java.util.List;

public interface IEventoXVecinoService {
    EventoXVecinoDTO registrar(EventoXVecinoDTO eventoXVecinoDTO);
    EventoXVecinoDTO modificar(EventoXVecinoDTO eventoXVecinoDTO);
    String eliminar(Integer idEXV);
    EventoXVecinoDTO buscarPorEventoYVecino(Integer idEvento, Integer idVecino);
    List<ComentariosEventoDTO> comentariosEvento(Integer idEvento);
    CantidadesVecinosPorEvento cantidadesVecinosPorEvento(Integer idEvento);
}
