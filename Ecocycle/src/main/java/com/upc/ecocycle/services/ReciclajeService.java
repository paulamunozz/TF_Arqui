package com.upc.ecocycle.services;

import com.upc.ecocycle.dto.CantidadReciclajeDTO;
import com.upc.ecocycle.dto.ReciclajeDTO;
import com.upc.ecocycle.enitites.Reciclaje;
import com.upc.ecocycle.instances.IReciclajeService;
import com.upc.ecocycle.repositories.EventoRepository;
import com.upc.ecocycle.repositories.EventoXVecinoRepository;
import com.upc.ecocycle.repositories.ReciclajeRepository;
import com.upc.ecocycle.repositories.VecinoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReciclajeService implements IReciclajeService {
    @Autowired
    ReciclajeRepository reciclajeRepository;
    @Autowired
    VecinoRepository  vecinoRepository;
    @Autowired
    EventoRepository eventoRepository;
    @Autowired
    EventoXVecinoRepository eventoXVecinoRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public String registrar(ReciclajeDTO reciclajeDTO) {
        if(!vecinoRepository.existsById(reciclajeDTO.getVecinoId())) {
            return "Este vecino no existe";
        }
        else if (reciclajeDTO.getFecha().isAfter(LocalDate.now()))
        {
            return "No puede ingresar una fecha futura";
        }

        Reciclaje reciclaje =  modelMapper.map(reciclajeDTO, Reciclaje.class);
        reciclaje.setPuntaje((int)Math.floor((reciclajeDTO.getPeso().divide(BigDecimal.TEN)).doubleValue()));
        reciclajeRepository.save(reciclaje);

        if(eventoXVecinoRepository.existsByVecinoIdAndEventoTipo(reciclajeDTO.getVecinoId(), reciclajeDTO.getTipo()))
        {

        }

        return "Reciclaje registrado";
    }

    @Override
    public String modificar(ReciclajeDTO reciclajeDTO) {
        if (!reciclajeRepository.existsById(reciclajeDTO.getIdReciclaje())) {
            return "Reciclaje no existe";
        }

        Reciclaje reciclaje = reciclajeRepository.findById(reciclajeDTO.getIdReciclaje()).orElse(null);
        reciclaje.setPeso((reciclajeDTO.getPeso() != null)
                ? reciclajeDTO.getPeso() : reciclaje.getPeso());
        reciclaje.setTipo(reciclajeDTO.getTipo() != null && !reciclajeDTO.getTipo().isBlank()
                ? reciclajeDTO.getTipo() : reciclaje.getTipo());
        reciclaje.setMetodo(reciclajeDTO.getMetodo() != null && !reciclajeDTO.getMetodo().isBlank()
                ? reciclajeDTO.getMetodo() : reciclaje.getMetodo());
        reciclaje.setFecha(reciclajeDTO.getFecha() != null
                ? reciclajeDTO.getFecha() : reciclaje.getFecha());

        if (reciclaje.getFecha().isAfter(LocalDate.now()))
        {
            return "No puede ingresar una fecha futura";
        }

        reciclajeRepository.save(reciclaje);
        return "Reciclaje modificado";
    }

    @Override
    public String eliminar(Integer idReciclaje) {
        if (idReciclaje==null) {
            return "Seleccione un registro de reciclaje";
        }
        else if (!reciclajeRepository.existsById(idReciclaje)) {
            return "El registro de reciclaje no existe";
        }
        else {
            reciclajeRepository.deleteById(idReciclaje);
            return "Registro de reciclaje eliminado correctamente";
        }
    }

    @Override
    public List<ReciclajeDTO> listarReciclajeVecino(Integer vecinoId) {
        if (vecinoId == null) {
            return null;
        }
        else if(!vecinoRepository.existsById(vecinoId)) {
            return null;
        }

        return reciclajeRepository.findAllByVecino(vecinoRepository.findById(vecinoId).orElse(null))
                .stream().map(reciclaje -> modelMapper.map(reciclaje, ReciclajeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReciclajeDTO> listarReciclajeDistrito(String distrito, String tipo, String metodo, LocalDate fechaInicio, LocalDate fechaFin, String genero, Integer edadMin, Integer edadMax) {
        if (fechaInicio.isAfter(fechaFin)){
            return null;
        }

        List<Reciclaje> lista = reciclajeRepository.findAll().stream()
                .filter(reciclaje -> distrito==null || reciclaje.getVecino().getDistrito().equals(distrito))
                .filter(reciclaje -> tipo==null || reciclaje.getTipo().equals(tipo))
                .filter(reciclaje -> metodo==null || reciclaje.getMetodo().equals(metodo))
                .filter(reciclaje -> fechaInicio==null || reciclaje.getFecha().isBefore(fechaFin) || reciclaje.getFecha().isEqual(fechaFin))
                .filter(reciclaje -> fechaFin==null || reciclaje.getFecha().isAfter(fechaInicio) || reciclaje.getFecha().isEqual(fechaInicio))
                .filter(reciclaje -> genero==null || reciclaje.getVecino().getGenero().equals(genero))
                .filter(reciclaje -> edadMin==null || reciclaje.getVecino().getEdad() >= edadMin)
                .filter(reciclaje -> edadMax==null || reciclaje.getVecino().getEdad() <= edadMax)
                .collect(Collectors.toList());

        return lista.stream().map(reciclaje -> modelMapper.map(reciclaje, ReciclajeDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<CantidadReciclajeDTO> listarCantidadReciclaje(String distrito) {
        return reciclajeRepository.listarCantidadReciclaje(distrito).stream()
                .map(cantidadReciclajeDTO -> modelMapper
                        .map(cantidadReciclajeDTO, CantidadReciclajeDTO.class))
                .collect(Collectors.toList());
    }
}
