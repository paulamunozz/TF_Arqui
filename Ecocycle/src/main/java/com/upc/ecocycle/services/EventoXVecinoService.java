package com.upc.ecocycle.services;

import com.upc.ecocycle.dto.EventoDTO;
import com.upc.ecocycle.dto.EventoXVecinoDTO;
import com.upc.ecocycle.dto.LogroDTO;
import com.upc.ecocycle.enitites.EventoXVecino;
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
        if(!eventoRepository.existsById(eventoXVecinoDTO.getEventoId())){
            return "Este evento no existe";
        }
        else if(!vecinoRepository.existsById(eventoXVecinoDTO.getVecinoId())) {
            return "Este vecino no existe";
        }

        EventoXVecino eventoXVecino = modelMapper.map(eventoXVecinoDTO, EventoXVecino.class);
        eventoXVecino.setEvento(eventoRepository.findById(eventoXVecinoDTO.getEventoId()).orElse(null));
        eventoXVecino.setVecino(vecinoRepository.findById(eventoXVecinoDTO.getVecinoId()).orElse(null));
        eventoXVecinoRepository.save(eventoXVecino);

        return "EXV registrado";
    }

    @Override
    public String modificar(EventoXVecinoDTO eventoXVecinoDTO) {
        if(!eventoXVecinoRepository.existsById(eventoXVecinoDTO.getIdEXV())){
            return "Este EXV no existe";
        }

        EventoXVecino eventoXVecino = eventoXVecinoRepository.findById(eventoXVecinoDTO.getIdEXV()).orElse(null);
        eventoXVecino.setComentario(eventoXVecinoDTO.getComentario());
        eventoXVecinoRepository.save(eventoXVecino);

        return "EXV modificado";
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
