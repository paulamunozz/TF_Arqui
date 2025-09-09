package com.upc.ecocycle.services;

import com.upc.ecocycle.dto.VecinoDTO;
import com.upc.ecocycle.enitites.Usuario;
import com.upc.ecocycle.enitites.Vecino;
import com.upc.ecocycle.instances.IVecinoService;
import com.upc.ecocycle.repositories.UsuarioRepository;
import com.upc.ecocycle.repositories.VecinoRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VecinoService implements IVecinoService {
    @Autowired private VecinoRepository vecinoRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public String registrar(VecinoDTO vecinoDTO) {
        if (vecinoDTO.getUsuarioId() == null
                || vecinoDTO.getNombre() == null || vecinoDTO.getNombre().isBlank()
                || vecinoDTO.getDireccion() == null || vecinoDTO.getDireccion().isBlank()
                || vecinoDTO.getDistrito() == null || vecinoDTO.getDistrito().isBlank()
                || vecinoDTO.getGenero() == null || vecinoDTO.getGenero().isBlank()
                || vecinoDTO.getEdad() == null || vecinoDTO.getEdad().isBlank()) {
            return "Ingrese todos los datos";
        }
        else if (!usuarioRepository.existsById(vecinoDTO.getUsuarioId())) {
            return "El usuario no existe";
        }
        else if (vecinoRepository.existsByUsuario(usuarioRepository.findById(
                vecinoDTO.getUsuarioId()).orElse(null)))
        {
            return "Este vecino ya existe";
        }
        else if (vecinoDTO.getEdad().matches("[0-9]")) {
            return "Formato de edad invalido";
        }
        else {
            Vecino vecino = modelMapper.map(vecinoDTO, Vecino.class);
            Usuario usuario = usuarioRepository.findById(vecinoDTO.getUsuarioId()).orElse(null);
            vecino.setUsuario(usuario);
            vecino.setEdad(Integer.parseInt(vecinoDTO.getEdad()));
            vecinoRepository.save(vecino);
            return "Vecino registrado exitosamente";
        }
    }

    @Override
    public String modificar(VecinoDTO vecinoDTO) {
        if (!usuarioRepository.existsById(vecinoDTO.getUsuarioId())) {
            return "El usuario no existe";
        }
        else if (!vecinoRepository.existsByUsuario(usuarioRepository.findById(
                vecinoDTO.getUsuarioId()).orElse(null)))
        {
            return "Este vecino no existe";
        }
        Usuario usuario = usuarioRepository.findById(vecinoDTO.getUsuarioId()).orElse(null);
        Vecino vecino = vecinoRepository.findByUsuario(usuario);
        vecino.setNombre((vecinoDTO.getNombre() != null && !vecinoDTO.getNombre().isBlank())
                        ? vecinoDTO.getNombre() : vecino.getNombre());
        vecino.setGenero((vecinoDTO.getGenero() != null && !vecinoDTO.getGenero().isBlank())
                        ? vecinoDTO.getGenero() : vecino.getGenero());
        vecino.setEdad((vecinoDTO.getEdad() != null && !vecinoDTO.getEdad().isBlank())
                        ? Integer.parseInt(vecinoDTO.getEdad()) : vecino.getEdad());
        vecino.setDistrito((vecinoDTO.getDistrito() != null && !vecinoDTO.getDistrito().isBlank())
                        ? vecinoDTO.getDistrito() : vecino.getDistrito());
        vecino.setDireccion((vecinoDTO.getDireccion() != null && !vecinoDTO.getDireccion().isBlank())
                        ? vecinoDTO.getDireccion() : vecino.getDireccion());
        vecino.setIcono(vecinoDTO.getIcono() != null ? vecinoDTO.getIcono() : vecino.getIcono());

        if (vecinoDTO.getEdad().matches("[0-9]")) {
            return "Formato de edad invalido";
        }
        else if (Integer.parseInt(vecinoDTO.getEdad()) < 0 || Integer.parseInt(vecinoDTO.getEdad()) >200) {
            return "Formato de edad invalido";
        }
        vecinoRepository.save(vecino);
        return "Vecino modificado exitosamente";
    }

    @Transactional
    @Override
    public String eliminar(Integer idVecino) {
        if (idVecino==null) {
            return "Seleccione un vecino";
        }
        else {
            return vecinoRepository.findById(idVecino).map(vecino ->
            {
                vecinoRepository.delete(vecino);
                return "Vecino eliminado exitosamente";
            }).orElse("No se encontró al vecino");
        }
    }

    @Override
    public VecinoDTO buscarPorCodigo(String codigoUsuario) {
        Usuario usuario = usuarioRepository.findByCodigo(codigoUsuario);
        Vecino vecino = vecinoRepository.findByUsuario(usuario);

        if (vecino == null) {
            return null;
        }
        else  {
            return modelMapper.map(vecino, VecinoDTO.class);
        }
    }

    @Transactional
    @Override
    public String actualizacionPuntos(Integer idVecino, Integer puntos) {
        return vecinoRepository.findById(idVecino).map(vecino ->
        {
            vecino.setPuntajetotal(vecino.getPuntajetotal() + puntos);
            vecinoRepository.save(vecino);
            return "Puntaje actualizado exitosamente";
        }).orElse("No se encontró al vecino");
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
    public List<VecinoDTO> rankingFiltrado(String distrito, String genero, Integer edadMin, Integer edadMax) {
        return vecinoRepository.findRankingFiltrado(distrito, genero, edadMin, edadMax).stream()
                .map(vecino -> modelMapper.map(vecino, VecinoDTO.class))
                .collect(Collectors.toList());
    }
}
