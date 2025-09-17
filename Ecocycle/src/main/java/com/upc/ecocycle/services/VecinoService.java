package com.upc.ecocycle.services;

import com.upc.ecocycle.dto.VecinoDTO;
import com.upc.ecocycle.enitites.Reciclaje;
import com.upc.ecocycle.enitites.Usuario;
import com.upc.ecocycle.enitites.Vecino;
import com.upc.ecocycle.instances.IVecinoService;
import com.upc.ecocycle.repositories.ReciclajeRepository;
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
    @Autowired private ReciclajeRepository reciclajeRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public String registrar(VecinoDTO vecinoDTO) {
        if (!usuarioRepository.existsById(vecinoDTO.getUsuarioId())) {
            return "El usuario no existe";
        }
        else if (vecinoRepository.existsByUsuario(usuarioRepository.findById(
                vecinoDTO.getUsuarioId()).orElse(null)))
        {
            return "Este vecino ya existe";
        }
        else {
            Vecino vecino = modelMapper.map(vecinoDTO, Vecino.class);
            Usuario usuario = usuarioRepository.findById(vecinoDTO.getUsuarioId()).orElse(null);
            vecino.setUsuario(usuario);
            vecino.setPuntajetotal(0);
            vecino.setIcono(0);
            vecino.setPuesto(0);
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
        vecino.setEdad((vecinoDTO.getEdad() != null)
                        ? vecinoDTO.getEdad() : vecino.getEdad());
        vecino.setDistrito((vecinoDTO.getDistrito() != null && !vecinoDTO.getDistrito().isBlank())
                        ? vecinoDTO.getDistrito() : vecino.getDistrito());
        vecino.setDireccion((vecinoDTO.getDireccion() != null && !vecinoDTO.getDireccion().isBlank())
                        ? vecinoDTO.getDireccion() : vecino.getDireccion());
        vecino.setIcono(vecinoDTO.getIcono() != null ? vecinoDTO.getIcono() : vecino.getIcono());

        vecinoRepository.save(vecino);
        return "Vecino modificado exitosamente";
    }

    @Transactional
    @Override
    public String eliminar(Integer idVecino) {
        Vecino vecino = vecinoRepository.findById(idVecino).orElse(null);
        if (vecino == null) {
            return "No se encontró el vecino";
        }

        vecinoRepository.deleteById(vecino.getId());
        return "Vecino eliminado exitosamente";
    }

    @Override
    public VecinoDTO buscarPorCodigo(String codigoUsuario) {
        if (codigoUsuario.isBlank()) {
            return null;
        }

        Usuario usuario = usuarioRepository.findByCodigo(codigoUsuario);
        Vecino vecino = vecinoRepository.findByUsuario(usuario);

        if (vecino == null) {
            return null;
        }
        else  {
            return modelMapper.map(vecino, VecinoDTO.class);
        }
    }

    @Override
    public VecinoDTO buscarPorId(Integer idVecino) {
        Vecino vecino = vecinoRepository.findById(idVecino).orElse(null);

        if (vecino == null) {
            return null;
        }
        else  {
            return modelMapper.map(vecino, VecinoDTO.class);
        }
    }

    @Override
    @Transactional
    public void actualizacionPuntos(Integer idVecino) {
        Vecino vecino = vecinoRepository.findById(idVecino)
                .orElseThrow(() -> new RuntimeException("No se encontró el vecino"));

        Integer puntajeTotal = reciclajeRepository.sumarPuntajePorVecino(idVecino);
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
    public List<VecinoDTO> rankingFiltrado(String distrito, String genero, Integer edadMin, Integer edadMax) {
        return vecinoRepository.findRankingFiltrado(distrito, genero, edadMin, edadMax).stream()
                .map(vecino -> modelMapper.map(vecino, VecinoDTO.class))
                .collect(Collectors.toList());
    }
}
