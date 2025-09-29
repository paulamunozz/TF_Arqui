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
            throw new RuntimeException("Este vecino no existe");
        }

        Evento evento = eventoRepository.findById(eventoId).get();
        List<EventoXVecino> exvs = eventoXVecinoRepository
                .findAllByVecino_IdAndEventoTipoAndEventoMetodo(vecinoId, evento.getTipo(), evento.getMetodo());

        for (EventoXVecino exv : exvs) {
            Evento eventoExistente = exv.getEvento();
            if (eventoExistente.getFechaFin().isAfter(evento.getFechaInicio())
                    && eventoExistente.getFechaInicio().isBefore(evento.getFechaFin())) {
                return "El vecino ya está en un evento de tipo " + evento.getTipo() +
                        " y método " + evento.getMetodo() + " que aún está activo";
            }
        }

        Vecino vecino = vecinoRepository.findById(vecinoId).get();
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
    public List<EventoXVecinoDTO> listarEXVPorEvento(Integer eventoId) {
        if (eventoId == null) {
            return null;
        }
        else if(!eventoRepository.existsById(eventoId)) {
            return null;
        }
        return eventoXVecinoRepository.findAllByEvento_Id(eventoId)
                .stream().map(exv -> modelMapper.map(exv, EventoXVecinoDTO.class))
                .collect(Collectors.toList());
    }
}
