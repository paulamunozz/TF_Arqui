package com.upc.ecocycle.services;

import com.upc.ecocycle.dto.EventoDTO;
import com.upc.ecocycle.enitites.Evento;
import com.upc.ecocycle.enitites.EventoXVecino;
import com.upc.ecocycle.instances.IEventoService;
import com.upc.ecocycle.repositories.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoService implements IEventoService {
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private MunicipalidadRepository municipalidadRepository;
    @Autowired
    private EventoXVecinoRepository eventoXVecinoRepository;
    @Autowired
    private ReciclajeRepository reciclajeRepository;
    @Autowired
    private VecinoRepository vecinoRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public String registrar(EventoDTO eventoDTO) {
        if (!municipalidadRepository.existsById(eventoDTO.getMunicipalidadId())) {
            return "Esta municipalidad no existe";
        } else if (eventoRepository.existsByNombre(eventoDTO.getNombre())) {
            return "Este evento ya existe";
        } else if (eventoDTO.getFechaInicio().isAfter(eventoDTO.getFechaFin())) {
            return "La fecha de inicio tiene que ser antes de la fecha de fin";
        } else if (eventoDTO.getBonificacion() <= 1) {
            return "La bonificación tiene que ser mayor que 1";
        } else {
            Evento evento = modelMapper.map(eventoDTO, Evento.class);
            evento.setMunicipalidad(municipalidadRepository.findById(eventoDTO.getMunicipalidadId()).get());
            evento.setPesoActual(BigDecimal.ZERO);
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
        Evento evento = eventoRepository.findById(eventoDTO.getIdEvento()).get();
        if (eventoRepository.existsByNombre(eventoDTO.getNombre()) && !eventoDTO.getNombre().equals(evento.getNombre())) {
            return "Este nombre ya ha sido registrado";
        }
        evento.setNombre((eventoDTO.getNombre() != null && !eventoDTO.getNombre().isBlank())
                ? eventoDTO.getNombre() : evento.getNombre());
        evento.setDescripcion((eventoDTO.getDescripcion() != null && !eventoDTO.getDescripcion().isBlank())
                ? eventoDTO.getDescripcion() : evento.getDescripcion());
        evento.setBonificacion((eventoDTO.getBonificacion() != null && eventoDTO.getBonificacion() > 1)
                ? eventoDTO.getBonificacion() : evento.getBonificacion());

        eventoRepository.save(evento);
        return "Evento modificado exitosamente";
    }

    @Override @Transactional
    public String eliminar(Integer id) {
        if (id == null) {
            return "Seleccione un evento";
        } else if (!eventoRepository.existsById(id)) {
            return "El evento no existe";
        } else {
            eventoXVecinoRepository.deleteAllByEvento_Id(id);
            eventoRepository.deleteById(id);
            return "Evento eliminado correctamente";
        }
    }

    @Override
    public EventoDTO buscarPorId(Integer id) {
        Evento evento = eventoRepository.findById(id).orElse(null);
        if (evento == null) {
            return null;
        } else {
            return modelMapper.map(evento, EventoDTO.class);
        }
    }

    public void actualizarPesoActual() {
        List<Evento> eventos = eventoRepository.findAll();

        for (Evento evento : eventos) {
            if ((evento.getFechaInicio().isBefore(LocalDate.now()) || evento.getFechaInicio().isEqual(LocalDate.now()))
                    && (evento.getFechaFin().isAfter(LocalDate.now()) || evento.getFechaFin().isEqual(LocalDate.now()))) {
                BigDecimal peso = reciclajeRepository.calcularPesoPorEvento(evento.getId());
                evento.setPesoActual(peso);
                evento.setSituacion(peso.compareTo(evento.getPesoObjetivo()) >= 0);
                eventoRepository.save(evento);
            }
        }
    }

    @Override
    public List<EventoDTO> buscarPorNombre(String nombre) {
        return eventoRepository.findAllByNombreContainingIgnoreCase(nombre).stream()
                .map(evento -> modelMapper.map(evento, EventoDTO.class))
                .collect(Collectors.toList());
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
                .toList();

        return lista.stream().map(evento -> modelMapper.map(evento, EventoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<EventoDTO> listarEventosPorVecino(Integer idVecino) {
        if (!vecinoRepository.existsById(idVecino)) {
            throw new RuntimeException("Este vecino no existe");
        }
        List<EventoXVecino> listaEXV = eventoXVecinoRepository.findAllByVecino_Id(idVecino).stream().toList();
        List<Evento> eventos = new ArrayList<>();
        for(EventoXVecino exv: listaEXV) {
            eventos.add(exv.getEvento());
        }
        return eventos.stream().map(evento -> modelMapper.map(evento, EventoDTO.class)).collect(Collectors.toList());
    }
}
