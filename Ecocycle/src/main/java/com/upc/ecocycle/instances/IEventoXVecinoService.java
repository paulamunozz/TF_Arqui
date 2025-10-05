package com.upc.ecocycle.instances;

import com.upc.ecocycle.dto.funcionalidades.ComentariosEventoDTO;
import com.upc.ecocycle.dto.EventoXVecinoDTO;

import java.util.List;

public interface IEventoXVecinoService {
    String registrar(EventoXVecinoDTO eventoXVecinoDTO);
    String modificar(EventoXVecinoDTO eventoXVecinoDTO);
    String eliminar(Integer idEXV);
    List<ComentariosEventoDTO> comentariosEvento(Integer idEvento);
}
