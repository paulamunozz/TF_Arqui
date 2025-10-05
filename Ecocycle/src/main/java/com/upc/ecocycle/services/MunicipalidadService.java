package com.upc.ecocycle.services;

import com.upc.ecocycle.dto.MunicipalidadDTO;
import com.upc.ecocycle.dto.VecinoDTO;
import com.upc.ecocycle.enitites.Municipalidad;
import com.upc.ecocycle.instances.IMunicipalidadService;
import com.upc.ecocycle.repositories.MunicipalidadRepository;
import com.upc.ecocycle.repositories.VecinoRepository;
import com.upc.ecocycle.security.entities.User;
import com.upc.ecocycle.security.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MunicipalidadService implements IMunicipalidadService {
    @Autowired private MunicipalidadRepository municipalidadRepository;
    @Autowired private VecinoRepository  vecinoRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder bcrypt;

    @Override
    public String modificarContrasena(Integer id, String contrasena) {
        Municipalidad municipalidad = municipalidadRepository.findById(id).orElse(null);
        if (municipalidad == null) {
            throw new RuntimeException("No existe la municipalidad con id: " + id);
        }
        User user = municipalidad.getUser();
        user.setPassword(bcrypt.encode(contrasena));
        userRepository.save(user);
        return "Contraseña actualizada";
    }

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
    public MunicipalidadDTO buscarPorId(Integer id) {
        Municipalidad municipalidad = municipalidadRepository.findById(id).orElse(null);
        if (municipalidad == null) {
            throw new RuntimeException("No existe el municipalidad con id: " + id);
        }
        MunicipalidadDTO municipalidadDTO =  modelMapper.map(municipalidad, MunicipalidadDTO.class);
        User user = municipalidad.getUser();
        municipalidadDTO.setCodigo(user.getUsername());
        municipalidadDTO.setContrasena(user.getPassword());
        return municipalidadDTO;
    }

    @Override
    public MunicipalidadDTO buscarPorCodigo(String codigo) {
        Municipalidad municipalidad = municipalidadRepository.findByUser_Username(codigo);
        if (municipalidad == null) {
            return null;
        }
        MunicipalidadDTO municipalidadDTO =  modelMapper.map(municipalidad, MunicipalidadDTO.class);
        User user = municipalidad.getUser();
        municipalidadDTO.setCodigo(user.getUsername());
        municipalidadDTO.setContrasena(user.getPassword());
        return municipalidadDTO;
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
