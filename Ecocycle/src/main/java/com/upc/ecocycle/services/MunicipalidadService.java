package com.upc.ecocycle.services;

import com.upc.ecocycle.dto.MunicipalidadDTO;
import com.upc.ecocycle.enitites.Municipalidad;
import com.upc.ecocycle.enitites.Usuario;
import com.upc.ecocycle.instances.IMunicipalidadService;
import com.upc.ecocycle.repositories.MunicipalidadRepository;
import com.upc.ecocycle.repositories.UsuarioRepository;
import com.upc.ecocycle.repositories.VecinoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MunicipalidadService implements IMunicipalidadService {
    @Autowired private MunicipalidadRepository municipalidadRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private VecinoRepository  vecinoRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public void actualizacionPuntos(String distrito) {
        if (!municipalidadRepository.existsByDistritoIgnoreCase(distrito.trim())) {
            throw new RuntimeException("Esta municipalidad no existe");
        }

        Integer totalPuntos = vecinoRepository.sumPuntosByMunicipalidad(distrito);
        if (totalPuntos == null) {
            totalPuntos = 0;
        }
        Municipalidad municipalidad = municipalidadRepository.findByDistritoIgnoreCase(distrito.trim());
        municipalidad.setPuntajetotal(totalPuntos);
        municipalidadRepository.save(municipalidad);
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
        Municipalidad municipalidad = municipalidadRepository.findByDistritoIgnoreCase(distrito.trim());

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
