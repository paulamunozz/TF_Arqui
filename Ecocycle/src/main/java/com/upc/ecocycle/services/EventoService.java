package com.upc.ecocycle.services;

import com.upc.ecocycle.enitites.Evento;
import com.upc.ecocycle.dto.EventoDTO;
import com.upc.ecocycle.enitites.Municipalidad;
import com.upc.ecocycle.instances.IEventoService;
import com.upc.ecocycle.repositories.EventoRepository;
import com.upc.ecocycle.repositories.MunicipalidadRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EventoService implements IEventoService {
    @Autowired
    EventoRepository eventoRepository;
    @Autowired
    MunicipalidadRepository municipalidadRepository;
    @Autowired
    ModelMapper modelMapper;


    @Override
    public String registrar(EventoDTO eventoDTO) {
        if (!municipalidadRepository.existsById(eventoDTO.getMunicipalidadId())) {
            return "Esta municipalidad no existe";
        }
        else if (eventoRepository.existsByNombre(eventoDTO.getNombre()))
        {
            return "Este evento ya existe";
        }
        else if (eventoDTO.getFechaInicio().isAfter(eventoDTO.getFechaFin())) {
            return "La fecha de inicio tiene que ser antes de la fecha de fin";
        }
        else {
            Evento evento = modelMapper.map(eventoDTO, Evento.class);
            evento.setMunicipalidad(municipalidadRepository.findById(eventoDTO.getMunicipalidadId()).orElse(null));
            evento.setSituacion(false);
            eventoRepository.save(evento);
            return "Evento registrado exitosamente";
        }
    }

    @Override
    public String modificar(EventoDTO eventoDTO) {
        if (!eventoRepository.existsById(eventoDTO.getIdEvento())) {
            return "El evento no existe";
        }

        Evento evento = eventoRepository.findById(eventoDTO.getIdEvento()).orElse(null);
        evento.setNombre((eventoDTO.getNombre() != null && !eventoDTO.getNombre().isBlank())
                ? eventoDTO.getNombre() : evento.getNombre());
        evento.setDescripcion((eventoDTO.getDescripcion() != null && !eventoDTO.getDescripcion().isBlank())
                ? eventoDTO.getDescripcion() : evento.getDescripcion());
        evento.setTipo((eventoDTO.getTipo() != null && !eventoDTO.getTipo().isBlank())
                ? eventoDTO.getTipo() : evento.getTipo());
        evento.setPesoObjetivo((eventoDTO.getPesoObjetivo() != null)
                ? eventoDTO.getPesoObjetivo() : evento.getPesoObjetivo());
        evento.setFechaInicio((eventoDTO.getFechaInicio() != null)
                ? eventoDTO.getFechaInicio() : evento.getFechaInicio());
        evento.setFechaFin((eventoDTO.getFechaFin() != null)
                ? eventoDTO.getFechaFin() : evento.getFechaFin());

        if (eventoRepository.existsByNombre(eventoDTO.getNombre()) && !Objects.equals(evento.getNombre(), eventoDTO.getNombre()))
        {
            return "Este evento ya existe";
        }
        else if (evento.getFechaInicio().isAfter(evento.getFechaFin())) {
            return "La fecha de inicio tiene que ser antes de la fecha de fin";
        }

        eventoRepository.save(evento);
        return "Evento modificado exitosamente";
    }

    @Override
    public String eliminar(Integer idEvento) {
        if (idEvento==null) {
            return "Seleccione un evento";
        }
        else if (!eventoRepository.existsById(idEvento)) {
            return "El evento no existe";
        }
        else {
            eventoRepository.deleteById(idEvento);
            return "Evento eliminado correctamente";
        }
    }

    @Override
    public EventoDTO buscarPorId(Integer idEvento) {
        Evento evento = eventoRepository.findById(idEvento).orElse(null);

        if (evento == null) {
            return null;
        }
        else {
            return modelMapper.map(evento, EventoDTO.class);
        }
    }

    @Override
    public EventoDTO actualizarPesoActual(Integer idEvento, BigDecimal peso) {
        Evento evento = eventoRepository.findById(idEvento).orElse(null);
        evento.setPesoActual(evento.getPesoActual().add(peso));
        eventoRepository.save(evento);

        if(!evento.getSituacion())
        {
            BigDecimal resultado = evento.getPesoObjetivo().subtract(evento.getPesoActual());
            if (resultado.compareTo(BigDecimal.ZERO) <= 0) {
                evento.setSituacion(true);
                eventoRepository.save(evento);
            }
        }

        return modelMapper.map(evento, EventoDTO.class);
    }

    @Override
    public List<EventoDTO> listarEventos(String nombre, String tipo, String estado, String distrito, LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio.isAfter(fechaFin)){
            return null;
        }

        LocalDate hoy = LocalDate.now();

        List<Evento> lista = eventoRepository.findAll().stream()
                .filter(evento -> nombre==null || evento.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .filter(evento -> distrito==null || evento.getMunicipalidad().getDistrito().equals(distrito))
                .filter(evento -> tipo==null || evento.getTipo().equals(tipo))
                .filter(evento -> {
                    if (estado==null) return true;
                    return switch (estado.toLowerCase())
                    {
                        case "pasado" -> evento.getFechaFin().isBefore(hoy);
                        case "activo" -> (evento.getFechaInicio().isBefore(hoy) || evento.getFechaInicio().isEqual(hoy))
                                && (evento.getFechaFin().isAfter(hoy) || evento.getFechaFin().isEqual(hoy));
                        case "futuro" -> evento.getFechaInicio().isAfter(hoy);
                        default -> true;
                    };
                })
                .filter(evento -> fechaInicio==null || evento.getFechaInicio().isBefore(fechaFin) || evento.getFechaInicio().isEqual(fechaFin))
                .filter(evento -> fechaFin==null || evento.getFechaFin().isAfter(fechaInicio) || evento.getFechaFin().isEqual(fechaInicio))
                .collect(Collectors.toList());

        return lista.stream().map(evento -> modelMapper.map(evento, EventoDTO.class)).collect(Collectors.toList());
    }
}
