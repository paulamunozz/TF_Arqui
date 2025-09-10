package com.upc.ecocycle.services;

import com.upc.ecocycle.dto.MunicipalidadDTO;
import com.upc.ecocycle.enitites.Municipalidad;
import com.upc.ecocycle.enitites.Usuario;
import com.upc.ecocycle.instances.IMunicipalidadService;
import com.upc.ecocycle.repositories.MunicipalidadRepository;
import com.upc.ecocycle.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MunicipalidadService implements IMunicipalidadService {
    @Autowired MunicipalidadRepository municipalidadRepository;
    @Autowired UsuarioRepository usuarioRepository;
    @Autowired ModelMapper modelMapper;

    @Override
    public String actualizacionPuntos(Integer idUsuario, Integer puntos) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            return "No se encontró el usuario";
        }

        Municipalidad municipalidad = municipalidadRepository.findByUsuario(usuario);
        if (municipalidad == null) {
            return "No se encontró el vecino";
        }

        municipalidad.setPuntajetotal(municipalidad.getPuntajetotal() + puntos);
        municipalidadRepository.save(municipalidad);
        return "Puntaje actualizado exitosamente";
    }

    @Override
    public void calcularPuestos() {
        List<Municipalidad> municipalidades = municipalidadRepository.findRanking();
        int puesto = 1;

        for (Municipalidad m : municipalidades) {
            m.setPuesto(puesto);
            municipalidadRepository.save(m);
            puesto++;
        }
    }

    @Override
    public MunicipalidadDTO buscarPorId(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        Municipalidad municipalidad = municipalidadRepository.findByUsuario(usuario);

        if (municipalidad == null) {
            return null;
        }
        else  {
            return modelMapper.map(municipalidad, MunicipalidadDTO.class);
        }
    }

    @Override
    public MunicipalidadDTO buscarPorCodigo(String codigoUsuario) {
        Usuario usuario = usuarioRepository.findByCodigo(codigoUsuario);
        Municipalidad municipalidad = municipalidadRepository.findByUsuario(usuario);

        if (municipalidad == null) {
            return null;
        }
        else {
            return modelMapper.map(municipalidad, MunicipalidadDTO.class);
        }
    }

    @Override
    public MunicipalidadDTO buscarPorDistrito(String distrito) {
        Municipalidad municipalidad = municipalidadRepository.findByDistrito(distrito);

        if (municipalidad == null) {
            return null;
        }
        else {
            return modelMapper.map(municipalidad, MunicipalidadDTO.class);
        }
    }

    @Override
    public List<MunicipalidadDTO> listarMunicipalidades() {
        return municipalidadRepository.findAll().stream()
                .map(municipalidad -> modelMapper.map(municipalidad, MunicipalidadDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MunicipalidadDTO> rankingMunicipalidades() {
        return municipalidadRepository.findRanking().stream()
                .map(municipalidad -> modelMapper.map(municipalidad, MunicipalidadDTO.class))
                .collect(Collectors.toList());
    }
}
