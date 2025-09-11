package com.upc.ecocycle.services;

import com.upc.ecocycle.dto.LogroDTO;
import com.upc.ecocycle.enitites.Logro;
import com.upc.ecocycle.instances.ILogroService;
import com.upc.ecocycle.repositories.LogroRepository;
import com.upc.ecocycle.repositories.VecinoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogroService implements ILogroService {
    @Autowired private LogroRepository logroRepository;
    @Autowired private VecinoRepository  vecinoRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public String registrarLogro(LogroDTO logroDTO) {
        if(!vecinoRepository.existsById(logroDTO.getVecinoId())) {
            return "Este vecino no existe";
        }

        Logro logro =  modelMapper.map(logroDTO, Logro.class);
        logro.setVecino(vecinoRepository.findById(logroDTO.getVecinoId()).orElse(null));
        logroRepository.save(logro);
        return "Logro registrado";
    }

    @Override
    public List<LogroDTO> listarLogrosVecino(Integer vecinoId) {
        if (vecinoId == null) {
            return null;
        }
        else if(!vecinoRepository.existsById(vecinoId)) {
            return null;
        }

        return logroRepository.findAllByVecino(vecinoRepository.findById(vecinoId).orElse(null))
                .stream().map(logro -> modelMapper.map(logro, LogroDTO.class))
                .collect(Collectors.toList());
    }
}
