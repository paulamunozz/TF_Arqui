package com.upc.ecocycle.services;

import com.upc.ecocycle.dto.LogroDTO;
import com.upc.ecocycle.dto.ReciclajeDTO;
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
    public LogroDTO registrarLogro(LogroDTO logroDTO) {
        if(!vecinoRepository.existsById(logroDTO.getVecinoId())) {
            throw new RuntimeException("No existe el vecino de id " + logroDTO.getVecinoId());
        }
        Logro logro =  modelMapper.map(logroDTO, Logro.class);
        logro.setVecino(vecinoRepository.findById(logroDTO.getVecinoId()).get());
        logroRepository.save(logro);
        return modelMapper.map(logro, LogroDTO.class);
    }

    @Override
    public List<LogroDTO> listarLogrosPorVecino(Integer vecinoId) {
        if (vecinoId == null) {
            return null;
        }
        else if(!vecinoRepository.existsById(vecinoId)) {
            return null;
        }

        return logroRepository.findAllByVecino_Id(vecinoId)
                .stream().map(logro -> modelMapper.map(logro, LogroDTO.class))
                .collect(Collectors.toList());
    }
}
