package com.upc.ecocycle.services;

import com.upc.ecocycle.dto.EventoDTO;
import com.upc.ecocycle.dto.VecinoDTO;
import com.upc.ecocycle.enitites.Evento;
import com.upc.ecocycle.enitites.EventoXVecino;
import com.upc.ecocycle.enitites.Vecino;
import com.upc.ecocycle.instances.IVecinoService;
import com.upc.ecocycle.repositories.EventoRepository;
import com.upc.ecocycle.repositories.EventoXVecinoRepository;
import com.upc.ecocycle.repositories.ReciclajeRepository;
import com.upc.ecocycle.repositories.VecinoRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VecinoService implements IVecinoService {
    @Autowired private VecinoRepository vecinoRepository;
    @Autowired private ReciclajeRepository reciclajeRepository;
    @Autowired private EventoXVecinoRepository eventoXVecinoRepository;
    @Autowired private EventoRepository eventoRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public String registrar(VecinoDTO vecinoDTO) {
        if (vecinoRepository.existsByDniAndEliminado((vecinoDTO.getDni()), false))
        {
            return "Este vecino ya existe";
        }
        Vecino vecino = modelMapper.map(vecinoDTO, Vecino.class);
        vecino.setPuntajetotal(0);
        vecino.setPuesto(0);
        vecino.setIcono(0);
        vecino.setEliminado(false);
        vecinoRepository.save(vecino);
        return "Vecino registrado exitosamente";
    }

    @Override
    public String modificar(VecinoDTO vecinoDTO) {
        if (!vecinoRepository.existsByIdAndEliminado(vecinoDTO.getIdVecino(), false)) {
            return "El vecino no existe";
        }
        Vecino vecino = vecinoRepository.findById(vecinoDTO.getIdVecino()).get();
        if(vecinoRepository.existsByDniAndEliminado((vecinoDTO.getDni()), false) && !vecinoDTO.getDni().equals(vecino.getDni())) {
            return "Este DNI ya ha sido registrado";
        }
        vecino.setDni((vecinoDTO.getDni() != null && !vecinoDTO.getDni().isBlank())
                ? vecinoDTO.getDni() : vecino.getDni());
        vecino.setContrasena((vecinoDTO.getContrasena() != null && !vecinoDTO.getContrasena().isBlank())
                ? vecinoDTO.getContrasena() : vecino.getContrasena());
        vecino.setNombre((vecinoDTO.getNombre() != null && !vecinoDTO.getNombre().isBlank())
                        ? vecinoDTO.getNombre() : vecino.getNombre());
        vecino.setGenero((vecinoDTO.getGenero() != null && !vecinoDTO.getGenero().isBlank())
                        ? vecinoDTO.getGenero() : vecino.getGenero());
        vecino.setEdad((vecinoDTO.getEdad() != null)
                        ? vecinoDTO.getEdad() : vecino.getEdad());
        vecino.setDistrito((vecinoDTO.getDistrito() != null && !vecinoDTO.getDistrito().isBlank())
                        ? vecinoDTO.getDistrito() : vecino.getDistrito());
        vecino.setIcono(vecinoDTO.getIcono() != null ? vecinoDTO.getIcono() : vecino.getIcono());

        vecinoRepository.save(vecino);
        return "Vecino modificado exitosamente";
    }

    @Transactional
    @Override
    public String eliminar(Integer id) {
        Vecino vecino = vecinoRepository.findById(id).orElse(null);
        if (vecino == null || vecino.getEliminado() == true) {
            return "No se encontró el vecino";
        }
        vecino.setEliminado(true);
        return "Vecino eliminado exitosamente";
    }

    @Override
    public VecinoDTO buscarPorDni(String dni) {
        if (dni.isBlank()) {
            return null;
        }
        Vecino vecino = vecinoRepository.findByDni(dni);
        if (vecino == null) {
            return null;
        }
        return modelMapper.map(vecino, VecinoDTO.class);
    }

    @Override
    public VecinoDTO buscarPorId(Integer id) {
        if (id==null) {
            return null;
        }
        Vecino vecino = vecinoRepository.findById(id).orElse(null);
        if (vecino == null) {
            return null;
        }
        return modelMapper.map(vecino, VecinoDTO.class);
    }

    @Override
    @Transactional
    public void actualizacionPuntos(Integer id) {
        Vecino vecino = vecinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el vecino"));

        Integer puntajeTotal = reciclajeRepository.sumarPuntajePorVecino(id);
        if (puntajeTotal == null) { puntajeTotal = 0;}

        vecino.setPuntajetotal(puntajeTotal);
        vecinoRepository.save(vecino);
    }

    @Override
    @Transactional
    public void calcularPuestos() {
        List<Vecino> vecinos = vecinoRepository.findRankingFiltrado(null, null, 0, 200);
        int puesto = 1;

        for (Vecino v : vecinos) {
            v.setPuesto(puesto);
            vecinoRepository.save(v);
            puesto++;
        }
    }

    @Override
    public List<VecinoDTO> listarVecinos() {
        return vecinoRepository.findAll().stream()
                .map(vecino -> modelMapper.map(vecino, VecinoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<VecinoDTO> listarVecinosPorEvento(Integer idEvento) {
        if (!eventoRepository.existsById(idEvento)) {
            throw new RuntimeException("Este evento no existe");
        }
        List<EventoXVecino> listaEXV = eventoXVecinoRepository.findAllByEvento_Id(idEvento).stream().toList();
        List<Vecino> vecinos = new ArrayList<>();
        for(EventoXVecino exv: listaEXV) {
            vecinos.add(exv.getVecino());
        }
        return vecinos.stream().map(vecino -> modelMapper.map(vecino, VecinoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<VecinoDTO> rankingFiltrado(String distrito, String genero, Integer edadMin, Integer edadMax) {
        return vecinoRepository.findRankingFiltrado(distrito, genero, edadMin, edadMax).stream()
                .map(vecino -> modelMapper.map(vecino, VecinoDTO.class))
                .collect(Collectors.toList());
    }
}
