package com.upc.ecocycle.services;

import com.upc.ecocycle.dto.EventoDTO;
import com.upc.ecocycle.dto.VecinoDTO;
import com.upc.ecocycle.dto.funcionalidades.CantidadEventosLogradosDTO;
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
    public EventoDTO registrar(EventoDTO eventoDTO) {
        if (!municipalidadRepository.existsById(eventoDTO.getMunicipalidadId())) {
            throw new RuntimeException("No existe una municipalidad con id " + eventoDTO.getMunicipalidadId());
        } else if (eventoRepository.existsByNombre(eventoDTO.getNombre())) {
            throw new RuntimeException("El evento '" + eventoDTO.getNombre() + "' ya ha sido registrado");
        } else if (eventoDTO.getFechaInicio().isAfter(eventoDTO.getFechaFin())) {
            throw new RuntimeException("La fecha de inicio no puede ser futura a la de fin");
        } else if (eventoDTO.getBonificacion() <= 1) {
            throw new RuntimeException("La bonificación tiene que ser mayor que 1");
        } else {
            Evento evento = modelMapper.map(eventoDTO, Evento.class);
            evento.setMunicipalidad(municipalidadRepository.findById(eventoDTO.getMunicipalidadId()).get());
            evento.setPesoActual(BigDecimal.ZERO);
            evento.setSituacion(false);
            eventoRepository.save(evento);
            return modelMapper.map(evento, EventoDTO.class);
        }
    }

    @Override
    public EventoDTO modificar(EventoDTO eventoDTO) {
        if (!eventoRepository.existsById(eventoDTO.getIdEvento())) {
            throw new RuntimeException("No existe un evento con id " + eventoDTO.getIdEvento());
        }
        Evento evento = eventoRepository.findById(eventoDTO.getIdEvento()).get();
        if (eventoRepository.existsByNombre(eventoDTO.getNombre()) && !eventoDTO.getNombre().equals(evento.getNombre())) {
            throw new RuntimeException("El evento con nombre '" +  eventoDTO.getNombre() + "' ya ha sido registrado");
        }
        evento.setNombre((eventoDTO.getNombre() != null && !eventoDTO.getNombre().isBlank())
                ? eventoDTO.getNombre() : evento.getNombre());
        evento.setDescripcion((eventoDTO.getDescripcion() != null && !eventoDTO.getDescripcion().isBlank())
                ? eventoDTO.getDescripcion() : evento.getDescripcion());
        evento.setBonificacion((eventoDTO.getBonificacion() != null && eventoDTO.getBonificacion() > 1)
                ? eventoDTO.getBonificacion() : evento.getBonificacion());

        eventoRepository.save(evento);
        return modelMapper.map(evento, EventoDTO.class);
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
            return "Evento eliminado exitosamente";
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
            BigDecimal peso = reciclajeRepository.calcularPesoPorEvento(evento.getId());
            evento.setPesoActual(peso);
            evento.setSituacion(peso.compareTo(evento.getPesoObjetivo()) >= 0);
            eventoRepository.save(evento);
        }
    }

    @Override
    public List<EventoDTO> listarEventos(String distrito, String nombre, String tipo, String metodo, LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio != null && fechaFin != null && fechaInicio.isAfter(fechaFin)){
            throw new RuntimeException("La fecha de inicio no puede ser futura a la de fin");
        }
        return eventoRepository.findAll().stream()
                .filter(evento -> nombre==null || evento.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .filter(evento -> distrito==null || evento.getMunicipalidad().getDistrito().equals(distrito))
                .filter(evento -> metodo==null || evento.getMetodo().equals(metodo))
                .filter(evento -> tipo==null || evento.getTipo().equals(tipo))
                .filter(evento -> fechaInicio == null || !evento.getFechaInicio().isBefore(fechaInicio))
                .filter(evento -> fechaFin == null || !evento.getFechaFin().isAfter(fechaFin))
                .map(evento -> modelMapper.map(evento, EventoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<EventoDTO> listarEventosPorVecino(Integer idVecino, String nombre, String tipo, String metodo, LocalDate fechaInicio, LocalDate fechaFin) {
        if (!vecinoRepository.existsById(idVecino)) {
            throw new RuntimeException("Este vecino no existe");
        }
        else
        if (fechaInicio != null && fechaFin != null && fechaInicio.isAfter(fechaFin)){
            throw new RuntimeException("La fecha de inicio no puede ser futura a la de fin");
        }
        List<EventoXVecino> listaEXV = eventoXVecinoRepository.findAllByVecino_Id(idVecino).stream().toList();
        List<Evento> eventos = new ArrayList<>();
        for(EventoXVecino exv: listaEXV) {
            eventos.add(exv.getEvento());
        }
        return eventos.stream()
                .filter(evento -> nombre==null || evento.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .filter(evento -> metodo==null || evento.getMetodo().equals(metodo))
                .filter(evento -> tipo==null || evento.getTipo().equals(tipo))
                .filter(evento -> fechaInicio == null || !evento.getFechaInicio().isBefore(fechaInicio))
                .filter(evento -> fechaFin == null || !evento.getFechaFin().isAfter(fechaFin))
                .map(evento -> modelMapper.map(evento, EventoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<EventoDTO> listarEventosDisponiblesParaVecino(Integer idVecino, String distrito, String nombre, String tipo, String metodo, LocalDate fechaInicio, LocalDate fechaFin) {
        if (!vecinoRepository.existsById(idVecino)) {
            throw new RuntimeException("Este vecino no existe");
        }
        else
        if (fechaInicio != null && fechaFin != null && fechaInicio.isAfter(fechaFin)){
            throw new RuntimeException("La fecha de inicio no puede ser futura a la de fin");
        }
        List<EventoXVecino> listaEXV = eventoXVecinoRepository.findAllByVecino_Id(idVecino).stream().toList();
        List<Evento> todosEventos = eventoRepository.findAllByMunicipalidad_Distrito(distrito).stream().toList();
        List<Evento> eventos = new ArrayList<>();
        for (Evento evento : todosEventos) {
            boolean participo = false;
            for (EventoXVecino exv : listaEXV) {
                if (exv.getEvento().getId().equals(evento.getId())) {
                    participo = true;
                    break;
                }
            }
            if (!participo) {
                eventos.add(evento);
            }
        }
        return eventos.stream()
                .filter(evento -> !evento.getFechaFin().isBefore(LocalDate.now()))
                .filter(evento -> nombre==null || evento.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .filter(evento -> metodo==null || evento.getMetodo().equals(metodo))
                .filter(evento -> tipo==null || evento.getTipo().equals(tipo))
                .filter(evento -> fechaInicio == null || !evento.getFechaInicio().isBefore(fechaInicio))
                .filter(evento -> fechaFin == null || !evento.getFechaFin().isAfter(fechaFin))
                .map(evento -> modelMapper.map(evento, EventoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CantidadEventosLogradosDTO cantidadEventosLogrados(String distrito) {
        return eventoRepository.cantidadEventosLogrados(distrito);
    }
}
