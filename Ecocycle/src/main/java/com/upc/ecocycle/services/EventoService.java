package com.upc.ecocycle.services;

import com.upc.ecocycle.dto.EventoDTO;
import com.upc.ecocycle.enitites.Evento;
import com.upc.ecocycle.enitites.Municipalidad;
import com.upc.ecocycle.instances.IEventoService;
import com.upc.ecocycle.repositories.EventoRepository;
import com.upc.ecocycle.repositories.MunicipalidadRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        else if (eventoDTO.getFechainicio().isAfter(eventoDTO.getFechafin())) {
            return "La fecha de inicio tiene que ser antes de la fecha de fin";
        }
        else {
            Evento evento = modelMapper.map(eventoDTO, Evento.class);
            Municipalidad municipalidad = municipalidadRepository.findById(eventoDTO.getMunicipalidadId()).orElse(null);
            evento.setMunicipalidad(municipalidad);
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
        evento.setPesoobjetivo((eventoDTO.getPesoobjetivo() != null)
                ? eventoDTO.getPesoobjetivo() : evento.getPesoobjetivo());
        evento.setFechainicio((eventoDTO.getFechainicio() != null)
                ? eventoDTO.getFechainicio() : evento.getFechainicio());
        evento.setFechafin((eventoDTO.getFechafin() != null)
                ? eventoDTO.getFechafin() : evento.getFechafin());

        if (eventoRepository.existsByNombre(eventoDTO.getNombre()) && !Objects.equals(evento.getNombre(), eventoDTO.getNombre()))
        {
            return "Este evento ya existe";
        }
        else if (evento.getFechainicio().isAfter(evento.getFechafin())) {
            return "La fecha de inicio tiene que ser antes de la fecha de fin";
        }

        eventoRepository.save(evento);
        return "Evento modificado exitosamente";
    }

    @Override
    public String eliminar(Integer idEvento) {
        Evento evento = eventoRepository.findById(idEvento).orElse(null);
        if (evento == null) {
            return "No se encontró el evento";
        }

        eventoRepository.delete(evento);
        return "Evento eliminado exitosamente";
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
    public List<EventoDTO> listarEventos(String nombre, String estado, String distrito, LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio.isAfter(fechaFin)){
            return null;
        }

        LocalDate hoy = LocalDate.now();

        List<Evento> lista = eventoRepository.findAll().stream()
                .filter(evento -> nombre==null || evento.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .filter(evento -> distrito==null || evento.getMunicipalidad().getDistrito().equals(distrito))
                .filter(evento -> {
                    if (estado==null) return true;
                    return switch (estado.toLowerCase())
                    {
                        case "pasado" -> evento.getFechafin().isBefore(hoy);
                        case "activo" -> (evento.getFechainicio().isBefore(hoy) || evento.getFechainicio().isEqual(hoy))
                                && (evento.getFechafin().isAfter(hoy) || evento.getFechafin().isEqual(hoy));
                        case "futuro" -> evento.getFechainicio().isAfter(hoy);
                        default -> true;
                    };
                })
                .filter(evento -> fechaInicio==null || evento.getFechainicio().isAfter(fechaInicio) || evento.getFechainicio().isEqual(fechaInicio))
                .filter(evento -> fechaFin==null || evento.getFechafin().isBefore(fechaFin) || evento.getFechafin().isEqual(fechaFin))
                .collect(Collectors.toList());

        return lista.stream().map(evento -> modelMapper.map(evento, EventoDTO.class)).collect(Collectors.toList());
    }
}
