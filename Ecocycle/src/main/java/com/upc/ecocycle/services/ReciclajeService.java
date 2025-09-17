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
            throw new RuntimeException("Este vecino no existe");
        }
        else if (reciclajeDTO.getFecha().isAfter(LocalDate.now())) {
            throw new RuntimeException("No puede ingresar una fecha futura");
        }

        Reciclaje reciclaje = modelMapper.map(reciclajeDTO, Reciclaje.class);

        int puntaje = (int) Math.floor(
                reciclajeDTO.getPeso().divide(BigDecimal.TEN).doubleValue()
        );

        EventoXVecino exv = eventoXVecinoRepository.findByVecinoIdAndEventoTipoAndEventoMetodo(
                reciclajeDTO.getVecinoId(),
                reciclajeDTO.getTipo(),
                reciclajeDTO.getMetodo()
        );
        if (exv != null && exv.getEvento() != null) {
            Evento evento = exv.getEvento();
            if ((evento.getFechaInicio().isBefore(reciclaje.getFecha()) || evento.getFechaInicio().isEqual(reciclaje.getFecha()))
                    && (evento.getFechaFin().isAfter(reciclaje.getFecha()) || evento.getFechaFin().isEqual(reciclaje.getFecha()))) {
                puntaje = (int) Math.floor(puntaje * evento.getBonificacion());
            }
        }

        reciclaje.setPuntaje(puntaje);
        reciclajeRepository.save(reciclaje);
        return modelMapper.map(reciclaje, ReciclajeDTO.class);
    }

    @Override
    public ReciclajeDTO modificar(ReciclajeDTO reciclajeDTO) {
        if (!reciclajeRepository.existsById(reciclajeDTO.getIdReciclaje())) {
            throw new RuntimeException("Este registro no existe");
        }
        else if (reciclajeDTO.getFecha()!=null && reciclajeDTO.getFecha().isAfter(LocalDate.now()))
        {
            throw new RuntimeException("No puede ingresar una fecha futura");
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

        int puntaje = (int) Math.floor(
                reciclajeDTO.getPeso().divide(BigDecimal.TEN).doubleValue()
        );

        EventoXVecino exv = eventoXVecinoRepository.findByVecinoIdAndEventoTipoAndEventoMetodo(
                reciclaje.getVecino().getId(),
                reciclaje.getTipo(),
                reciclaje.getMetodo()
        );

        if (exv != null && exv.getEvento() != null) {
            Evento evento = exv.getEvento();

            if ((evento.getFechaInicio().isBefore(reciclaje.getFecha()) || evento.getFechaInicio().isEqual(reciclaje.getFecha()))
                    && (evento.getFechaFin().isAfter(reciclaje.getFecha()) || evento.getFechaFin().isEqual(reciclaje.getFecha()))) {
                puntaje = (int) Math.floor(puntaje * evento.getBonificacion());
            }
        }

        reciclaje.setPuntaje(puntaje);
        reciclajeRepository.save(reciclaje);
        return modelMapper.map(reciclaje, ReciclajeDTO.class);
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
    public ReciclajeDTO buscarPorId(Integer idReciclaje) {
        return reciclajeRepository.findById(idReciclaje)
                .map(reciclaje -> modelMapper.map(reciclaje, ReciclajeDTO.class))
                .orElseThrow(() -> new RuntimeException("Registro no existe"));
    }

    @Override
    public List<ReciclajeDTO> listarReciclajeVecino(Integer vecinoId) {
        if (vecinoId == null) {
            return null;
        }
        else if(!vecinoRepository.existsById(vecinoId)) {
            return null;
        }

        return reciclajeRepository.findAllByVecinoId(vecinoId)
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
