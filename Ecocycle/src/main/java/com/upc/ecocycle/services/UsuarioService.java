package com.upc.ecocycle.services;

import com.upc.ecocycle.dto.UsuarioDTO;
import com.upc.ecocycle.enitites.Usuario;
import com.upc.ecocycle.instances.IUsuarioService;
import com.upc.ecocycle.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements IUsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsuarioDTO registrar(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getIdUsuario() == null || usuarioDTO.getContrasena().isBlank()) {
            System.out.println("Ingrese todos los datos");
            return null;
        } else if (String.valueOf(usuarioDTO.getIdUsuario()).length() != 8 ||
                !String.valueOf(usuarioDTO.getIdUsuario()).matches("[0-9]{8}")) {
            System.out.println("Ingrese su DNI correctamente");
            return null;
        }
        else  {
            Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
            usuario =  usuarioRepository.save(usuario);
            return modelMapper.map(usuario, UsuarioDTO.class);
        }
    }

    @Override
    public UsuarioDTO modificar(Usuario usuario) {
        return null;
    }

    @Override
    public void eliminar(Integer id) {

    }

    @Override
    public UsuarioDTO buscarPorId(Integer id) {
        return null;
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return List.of();
    }
}
