package com.upc.ecocycle.services;

import com.upc.ecocycle.dto.CantidadReciclajeDTO;
import com.upc.ecocycle.dto.ReciclajeDTO;
import com.upc.ecocycle.enitites.Evento;
import com.upc.ecocycle.enitites.EventoXVecino;
import com.upc.ecocycle.enitites.Reciclaje;
import com.upc.ecocycle.instances.IReciclajeService;
import com.upc.ecocycle.repositories.EventoXVecinoRepository;
import com.upc.ecocycle.repositories.ReciclajeRepository;
import com.upc.ecocycle.repositories.VecinoRepository;
import jakarta.transaction.Transactional;
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
    private ReciclajeRepository reciclajeRepository;
    @Autowired
    private VecinoRepository  vecinoRepository;
    @Autowired
    private EventoXVecinoRepository eventoXVecinoRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReciclajeDTO registrar(ReciclajeDTO reciclajeDTO) {
        if (!vecinoRepository.existsById(reciclajeDTO.getVecinoId())) {
            throw new RuntimeException("No existe el vecino de id " + reciclajeDTO.getVecinoId());
        }

        Reciclaje reciclaje = modelMapper.map(reciclajeDTO, Reciclaje.class);
        reciclaje.setFecha(LocalDate.now());

        int puntaje = (int) Math.floor(
                reciclajeDTO.getPeso().divide(BigDecimal.TEN).doubleValue()
        );

        List<EventoXVecino> exvs = eventoXVecinoRepository.findAllByVecino_IdAndEventoTipoAndEventoMetodo(
                        reciclajeDTO.getVecinoId(),
                        reciclajeDTO.getTipo(),
                        reciclajeDTO.getMetodo()
                );
        for (EventoXVecino exv : exvs) {
            Evento evento = exv.getEvento();
            if (evento != null) {
                if (!evento.getFechaInicio().isAfter(reciclaje.getFecha())
                        && !evento.getFechaFin().isBefore(reciclaje.getFecha())) {
                    puntaje = (int) Math.floor(puntaje * evento.getBonificacion());
                    break;
                }
            }
        }
        reciclaje.setPuntaje(puntaje);
        reciclajeRepository.save(reciclaje);
        return modelMapper.map(reciclaje, ReciclajeDTO.class);
    }

    @Override
    public ReciclajeDTO modificar(ReciclajeDTO reciclajeDTO) {
        if (!reciclajeRepository.existsById(reciclajeDTO.getIdReciclaje())) {
            throw new RuntimeException("No existe el registro de id " + reciclajeDTO.getIdReciclaje());
        }
        Reciclaje reciclaje = reciclajeRepository.findById(reciclajeDTO.getIdReciclaje()).orElse(null);
        reciclaje.setPeso((reciclajeDTO.getPeso() != null)
                ? reciclajeDTO.getPeso() : reciclaje.getPeso());
        reciclaje.setTipo(reciclajeDTO.getTipo() != null && !reciclajeDTO.getTipo().isBlank()
                ? reciclajeDTO.getTipo() : reciclaje.getTipo());
        reciclaje.setMetodo(reciclajeDTO.getMetodo() != null && !reciclajeDTO.getMetodo().isBlank()
                ? reciclajeDTO.getMetodo() : reciclaje.getMetodo());

        int puntaje = (int) Math.floor(
                reciclajeDTO.getPeso().divide(BigDecimal.TEN).doubleValue()
        );
        List<EventoXVecino> exvs = eventoXVecinoRepository.findAllByVecino_IdAndEventoTipoAndEventoMetodo(
                reciclajeDTO.getVecinoId(),
                reciclajeDTO.getTipo(),
                reciclajeDTO.getMetodo()
        );
        for (EventoXVecino exv : exvs) {
            Evento evento = exv.getEvento();
            if (evento != null) {
                if (!evento.getFechaInicio().isAfter(reciclaje.getFecha())
                        && !evento.getFechaFin().isBefore(reciclaje.getFecha())) {
                    puntaje = (int) Math.floor(puntaje * evento.getBonificacion());
                    break;
                }
            }
        }
        reciclaje.setPuntaje(puntaje);
        reciclajeRepository.save(reciclaje);
        return modelMapper.map(reciclaje, ReciclajeDTO.class);
    }

    @Override @Transactional
    public String eliminar(Integer id) {
        if (id ==null) {
            return "Seleccione un registro de reciclaje";
        }
        else if (!reciclajeRepository.existsById(id)) {
            return "No existe el registro de id " + id;
        }
        else {
            reciclajeRepository.deleteById(id);
            return "El registro de reciclaje ha sido eliminado exitosamente";
        }
    }

    @Override
    public ReciclajeDTO buscarPorId(Integer id) {
        return reciclajeRepository.findById(id)
                .map(reciclaje -> modelMapper.map(reciclaje, ReciclajeDTO.class))
                .orElseThrow(() -> new RuntimeException("No existe el registro de id " + id));
    }

    @Override
    public List<ReciclajeDTO> listarReciclajePorVecino(Integer vecinoId, String tipo, String metodo, LocalDate fechaInicio, LocalDate fechaFin) {
        if (vecinoId == null) {
            throw new RuntimeException("Seleccione un vecino");
        }
        else if(!vecinoRepository.existsById(vecinoId)) {
            throw new RuntimeException("No existe el vecino de id " + vecinoId);
        }
        else if (fechaInicio != null && fechaFin != null && fechaInicio.isAfter(fechaFin)){
            throw new RuntimeException("La fecha de inicio no puede ser futura a la de fin");
        }

        return reciclajeRepository.findAllByVecinoId(vecinoId)
                .stream()
                .filter(reciclaje -> tipo==null || reciclaje.getTipo().equals(tipo))
                .filter(reciclaje -> metodo==null || reciclaje.getMetodo().equals(metodo))
                .filter(reciclaje -> fechaInicio == null || !reciclaje.getFecha().isBefore(fechaInicio))
                .filter(reciclaje -> fechaFin == null || !reciclaje.getFecha().isAfter(fechaFin))
                .map(reciclaje -> modelMapper.map(reciclaje, ReciclajeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReciclajeDTO> listarReciclajeFiltrado(String distrito, String tipo, String metodo, LocalDate fechaInicio, LocalDate fechaFin, String genero, Integer edadMin, Integer edadMax) {
        if (fechaInicio != null && fechaFin != null && fechaInicio.isAfter(fechaFin)){
            throw new RuntimeException("La fecha de inicio no puede ser futura a la de fin");
        }

        return reciclajeRepository.findAll().stream()
                .filter(reciclaje -> distrito==null || reciclaje.getVecino().getDistrito().equals(distrito))
                .filter(reciclaje -> tipo==null || reciclaje.getTipo().equals(tipo))
                .filter(reciclaje -> metodo==null || reciclaje.getMetodo().equals(metodo))
                .filter(reciclaje -> fechaInicio == null || !reciclaje.getFecha().isBefore(fechaInicio))
                .filter(reciclaje -> fechaFin == null || !reciclaje.getFecha().isAfter(fechaFin))
                .filter(reciclaje -> genero==null || reciclaje.getVecino().getGenero().equals(genero))
                .filter(reciclaje -> edadMin==null || reciclaje.getVecino().getEdad() >= edadMin)
                .filter(reciclaje -> edadMax==null || reciclaje.getVecino().getEdad() <= edadMax)
                .map(reciclaje -> modelMapper.map(reciclaje, ReciclajeDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<CantidadReciclajeDTO> listarCantidadReciclaje(String distrito) {
        return reciclajeRepository.listarCantidadReciclaje(distrito);
    }
}
