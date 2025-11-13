package com.upc.ecocycle.services;

import com.upc.ecocycle.dto.ReciclajeDTO;
import com.upc.ecocycle.dto.funcionalidades.CantidadesVecinosPorEvento;
import com.upc.ecocycle.dto.funcionalidades.ComentariosEventoDTO;
import com.upc.ecocycle.dto.EventoXVecinoDTO;
import com.upc.ecocycle.enitites.Evento;
import com.upc.ecocycle.enitites.EventoXVecino;
import com.upc.ecocycle.enitites.Logro;
import com.upc.ecocycle.enitites.Vecino;
import com.upc.ecocycle.instances.IEventoXVecinoService;
import com.upc.ecocycle.repositories.EventoRepository;
import com.upc.ecocycle.repositories.EventoXVecinoRepository;
import com.upc.ecocycle.repositories.LogroRepository;
import com.upc.ecocycle.repositories.VecinoRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoXVecinoService implements IEventoXVecinoService {
    @Autowired
    private EventoXVecinoRepository eventoXVecinoRepository;
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private VecinoRepository vecinoRepository;
    @Autowired
    private LogroRepository logroRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public EventoXVecinoDTO registrar(EventoXVecinoDTO eventoXVecinoDTO) {
        int eventoId = eventoXVecinoDTO.getEventoId();
        int vecinoId = eventoXVecinoDTO.getVecinoId();

        if (!eventoRepository.existsById(eventoId))
        {
            throw new RuntimeException("Este evento no existe");
        }
        else if (!vecinoRepository.existsById(vecinoId))
        {
            throw new RuntimeException("Este vecino no existe");
        }
        else if (eventoXVecinoRepository.existsByEvento_IdAndVecino_Id(eventoId, vecinoId)) {
            throw new RuntimeException("Ya se encuentra registrado en este evento");
        }

        Evento evento = eventoRepository.findById(eventoId).get();
        List<EventoXVecino> exvs = eventoXVecinoRepository
                .findAllByVecino_IdAndEventoTipoAndEventoMetodo(vecinoId, evento.getTipo(), evento.getMetodo());

        for (EventoXVecino exv : exvs) {
            Evento eventoExistente = exv.getEvento();
            if (eventoExistente.getFechaFin().isAfter(evento.getFechaInicio())
                    && eventoExistente.getFechaInicio().isBefore(evento.getFechaFin())) {
                throw new RuntimeException("El vecino ya está en un evento de tipo " + evento.getTipo() +
                        " y método " + evento.getMetodo() + " que aún está activo");
            }
        }

        Vecino vecino = vecinoRepository.findById(vecinoId).get();
        EventoXVecino eventoXVecino = modelMapper.map(eventoXVecinoDTO, EventoXVecino.class);
        eventoXVecino.setEvento(evento);
        eventoXVecino.setVecino(vecino);
        eventoXVecinoRepository.save(eventoXVecino);

        String nombreLogro = "Participaste en el evento " + evento.getNombre();
        if(!logroRepository.existsByVecino_IdAndNombre(vecinoId, nombreLogro))
        {
            Logro logro = new Logro();
            logro.setVecino(vecino);
            logro.setNombre(nombreLogro);
            logroRepository.save(logro);
        }
        return modelMapper.map(eventoXVecino, EventoXVecinoDTO.class);
    }

    @Override
    public EventoXVecinoDTO modificar(EventoXVecinoDTO eventoXVecinoDTO) {
        EventoXVecino eventoXVecino = eventoXVecinoRepository.findById(eventoXVecinoDTO.getId())
                .orElseThrow(() -> new RuntimeException("Este EXV no existe"));

        eventoXVecino.setComentario(eventoXVecinoDTO.getComentario());
        eventoXVecinoRepository.save(eventoXVecino);

        return modelMapper.map(eventoXVecino, EventoXVecinoDTO.class);
    }

    @Override @Transactional
    public String eliminar(Integer idEXV) {
        if (idEXV==null) {
            return "Seleccione un EXV";
        }
        else if(!eventoXVecinoRepository.existsById(idEXV)){
            return "Este EXV no existe";
        }
        String eventoNombre = eventoRepository.findById(eventoXVecinoRepository.findById(idEXV).get().getEvento().getId()).get().getNombre();
        eventoXVecinoRepository.deleteById(idEXV);
        return "Ya no forma parte del evento " + eventoNombre;
    }

    @Override
    public EventoXVecinoDTO buscarPorEventoYVecino(Integer idEvento, Integer idVecino) {
        if (idEvento == null || idVecino == null) {
            throw new RuntimeException("Seleccione un EXV");
        }
        else if(!eventoRepository.existsById(idEvento) || !vecinoRepository.existsById(idVecino)) {
            throw new RuntimeException("Seleccione un EXV");
        }
        EventoXVecino exv = eventoXVecinoRepository.findAllByEvento_IdAndVecino_Id(idEvento, idVecino);
        return modelMapper.map(exv, EventoXVecinoDTO.class);
    }

    @Override
    public List<ComentariosEventoDTO> comentariosEvento(Integer idEvento) {
        if (idEvento == null) {
            throw new RuntimeException("Seleccione un EXV");
        }
        else if(!eventoRepository.existsById(idEvento)) {
            throw new RuntimeException("Seleccione un EXV");
        }
        return eventoXVecinoRepository.comentariosEvento(idEvento);
    }

    @Override
    public CantidadesVecinosPorEvento cantidadesVecinosPorEvento(Integer idEvento) {
        if (idEvento == null) {
            throw new RuntimeException("Seleccione un EXV");
        }
        else if(!eventoRepository.existsById(idEvento)) {
            throw new RuntimeException("Seleccione un EXV");
        }
        return eventoXVecinoRepository.cantidadesVecinosPorEvento(idEvento);
    }
}
