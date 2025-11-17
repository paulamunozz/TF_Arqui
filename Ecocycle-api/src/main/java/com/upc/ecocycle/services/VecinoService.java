package com.upc.ecocycle.services;

import com.upc.ecocycle.dto.EventoDTO;
import com.upc.ecocycle.dto.ReciclajeDTO;
import com.upc.ecocycle.dto.VecinoDTO;
import com.upc.ecocycle.enitites.Evento;
import com.upc.ecocycle.enitites.EventoXVecino;
import com.upc.ecocycle.enitites.Vecino;
import com.upc.ecocycle.instances.IVecinoService;
import com.upc.ecocycle.repositories.EventoRepository;
import com.upc.ecocycle.repositories.EventoXVecinoRepository;
import com.upc.ecocycle.repositories.ReciclajeRepository;
import com.upc.ecocycle.repositories.VecinoRepository;
import com.upc.ecocycle.security.entities.User;
import com.upc.ecocycle.security.repositories.RoleRepository;
import com.upc.ecocycle.security.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VecinoService implements IVecinoService {
    @Autowired private VecinoRepository vecinoRepository;
    @Autowired private ReciclajeRepository reciclajeRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder bcrypt;

    @Override
    public VecinoDTO registrar(VecinoDTO vecinoDTO) {
        if (vecinoRepository.existsByUser_UsernameAndEliminado((vecinoDTO.getDni()), false))
        {
            throw new RuntimeException("Ya existe el vecino de DNI " + vecinoDTO.getDni());
        }
        User user = new User();
        user.setUsername(vecinoDTO.getDni());
        user.setPassword(bcrypt.encode(vecinoDTO.getContrasena()));
        user = userRepository.save(user);
        userRepository.insertUserRole(user.getId(), 3);

        Vecino vecino = modelMapper.map(vecinoDTO, Vecino.class);
        vecino.setPuntajetotal(0);
        vecino.setPuesto(0);
        vecino.setIcono(0);
        vecino.setEliminado(false);
        vecino.setUser(user);
        vecino = vecinoRepository.save(vecino);

        return modelMapper.map(vecino, VecinoDTO.class);
    }

    @Override
    public VecinoDTO modificar(VecinoDTO vecinoDTO) {
        if (!vecinoRepository.existsByIdAndEliminado(vecinoDTO.getIdVecino(), false)) {
            throw new RuntimeException("No existe el registro de id " + vecinoDTO.getIdVecino());
        }

        Vecino vecino = vecinoRepository.findById(vecinoDTO.getIdVecino()).get();
        User user = vecino.getUser();
        if(vecinoRepository.existsByUser_UsernameAndEliminado((vecinoDTO.getDni()), false) && !vecinoDTO.getDni().equals(user.getUsername())) {
            throw new RuntimeException("El DNI " + vecinoDTO.getDni() + " ya ha sido registrado");
        }
        user.setUsername((vecinoDTO.getDni() != null && !vecinoDTO.getDni().isBlank())
                ? vecinoDTO.getDni() : user.getUsername());
        user.setPassword((vecinoDTO.getContrasena() != null && !vecinoDTO.getContrasena().isBlank())
                ? bcrypt.encode(vecinoDTO.getContrasena()) : user.getPassword());

        vecino.setNombre((vecinoDTO.getNombre() != null && !vecinoDTO.getNombre().isBlank())
                        ? vecinoDTO.getNombre() : vecino.getNombre());
        vecino.setGenero((vecinoDTO.getGenero() != null && !vecinoDTO.getGenero().isBlank())
                        ? vecinoDTO.getGenero() : vecino.getGenero());
        vecino.setEdad((vecinoDTO.getEdad() != null)
                        ? vecinoDTO.getEdad() : vecino.getEdad());
        vecino.setDistrito((vecinoDTO.getDistrito() != null && !vecinoDTO.getDistrito().isBlank())
                        ? vecinoDTO.getDistrito() : vecino.getDistrito());
        vecino.setIcono(vecinoDTO.getIcono() != null ? vecinoDTO.getIcono() : vecino.getIcono());

        userRepository.save(user);
        vecinoRepository.save(vecino);
        return modelMapper.map(vecino, VecinoDTO.class);
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
            throw new RuntimeException("Ingrese su DNI");
        }
        Vecino vecino = vecinoRepository.findByUser_Username(dni);
        if (vecino == null) {
            throw new RuntimeException("Este DNI no se encuentra registrado");
        } else if (vecino.getEliminado()==true) {
            throw new RuntimeException("Este vecino no existe");
        }

        VecinoDTO vecinoDTO =  modelMapper.map(vecino, VecinoDTO.class);
        User user = vecino.getUser();
        vecinoDTO.setDni(user.getUsername());
        vecinoDTO.setContrasena(user.getPassword());
        return vecinoDTO;
    }

    @Override
    public VecinoDTO buscarPorId(Integer id) {
        if (id==null) {
            return null;
        }
        Vecino vecino = vecinoRepository.findById(id).orElse(null);
        if (vecino == null) {
            return null;
        }else if (vecino.getEliminado()==true) {
            throw new RuntimeException("Este vecino no existe");
        }

        VecinoDTO vecinoDTO =  modelMapper.map(vecino, VecinoDTO.class);
        User user = vecino.getUser();
        vecinoDTO.setDni(user.getUsername());
        vecinoDTO.setContrasena(user.getPassword());
        return vecinoDTO;
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
    public List<VecinoDTO> rankingFiltrado(String distrito, String genero, Integer edadMin, Integer edadMax) {
        return vecinoRepository.findRankingFiltrado(distrito, genero, edadMin, edadMax).stream()
                .map(vecino -> modelMapper.map(vecino, VecinoDTO.class))
                .collect(Collectors.toList());
    }
}
