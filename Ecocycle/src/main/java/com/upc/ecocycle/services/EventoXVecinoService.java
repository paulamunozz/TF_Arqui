package com.upc.ecocycle.services;

import com.upc.ecocycle.dto.EventoXVecinoDTO;
import com.upc.ecocycle.enitites.Evento;
import com.upc.ecocycle.enitites.EventoXVecino;
import com.upc.ecocycle.enitites.Vecino;
import com.upc.ecocycle.instances.IEventoXVecinoService;
import com.upc.ecocycle.repositories.EventoRepository;
import com.upc.ecocycle.repositories.EventoXVecinoRepository;
import com.upc.ecocycle.repositories.VecinoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoXVecinoService implements IEventoXVecinoService {
    @Autowired
    private EventoXVecinoRepository eventoXVecinoRepository;
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private VecinoRepository vecinoRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String registrar(EventoXVecinoDTO eventoXVecinoDTO) {
        Evento evento = eventoRepository.findById(eventoXVecinoDTO.getEventoId())
                .orElseThrow(() -> new RuntimeException("Este evento no existe"));
        Vecino vecino = vecinoRepository.findById(eventoXVecinoDTO.getVecinoId())
                .orElseThrow(() -> new RuntimeException("Este vecino no existe"));

        if (eventoXVecinoRepository.existsByEventoAndVecino(evento, vecino)) {
            return "Ya se ha registrado en este evento";
        }

        EventoXVecino exv = eventoXVecinoRepository.findByVecinoIdAndEventoTipoAndEventoMetodo(
                eventoXVecinoDTO.getVecinoId(),
                evento.getTipo(),
                evento.getMetodo()
        );

        if (exv != null) {
            Evento eventoExistente = exv.getEvento();

            if (eventoExistente.getFechaFin().isAfter(evento.getFechaInicio())
                    && eventoExistente.getFechaInicio().isBefore(evento.getFechaFin())) {
                return "El vecino ya está en un evento de tipo " + evento.getTipo() +
                        " y método " + evento.getMetodo() + " que aún está activo";
            }
        }

        EventoXVecino eventoXVecino = modelMapper.map(eventoXVecinoDTO, EventoXVecino.class);
        eventoXVecino.setEvento(evento);
        eventoXVecino.setVecino(vecino);
        eventoXVecinoRepository.save(eventoXVecino);
        return "EXV registrado";
    }

    @Override
    public String modificar(EventoXVecinoDTO eventoXVecinoDTO) {
        EventoXVecino eventoXVecino = eventoXVecinoRepository.findById(eventoXVecinoDTO.getIdEXV())
                .orElseThrow(() -> new RuntimeException("Este EXV no existe"));

        eventoXVecino.setComentario(eventoXVecinoDTO.getComentario());
        eventoXVecinoRepository.save(eventoXVecino);

        return "Comentario modificado";
    }

    @Override
    public String eliminar(Integer idEXV) {
        if (idEXV==null) {
            return "Seleccione un evento";
        }
        else if(!eventoXVecinoRepository.existsById(idEXV)){
            return "Este EXV no existe";
        }
        eventoXVecinoRepository.deleteById(idEXV);
        return "EXV eliminado";
    }

    @Override
    public List<EventoXVecinoDTO> listarEXVPorEvento(Integer eventoId) {
        if (eventoId == null) {
            return null;
        }
        else if(!eventoRepository.existsById(eventoId)) {
            return null;
        }

        return eventoXVecinoRepository.findAllByEvento(eventoRepository.findById(eventoId).orElse(null))
                .stream().map(exv -> modelMapper.map(exv, EventoXVecinoDTO.class))
                .collect(Collectors.toList());
    }
}
