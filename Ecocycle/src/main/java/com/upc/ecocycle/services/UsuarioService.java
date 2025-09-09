package com.upc.ecocycle.services;

import com.upc.ecocycle.dto.UsuarioDTO;
import com.upc.ecocycle.enitites.Usuario;
import com.upc.ecocycle.instances.IUsuarioService;
import com.upc.ecocycle.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements IUsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String registrar(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getCodigo().isBlank() || usuarioDTO.getContrasena().isBlank()) {
            return "Ingrese todos los datos";
        }
        else if (!usuarioDTO.getCodigo().matches("[0-9]{8}")) {
            return "Ingrese su DNI correctamente";
        }
        else if (usuarioRepository.existsByCodigo(usuarioDTO.getCodigo())) {
            return "El usuario ya existe";
        }
        else  {
            Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
            usuarioRepository.save(usuario);
            return "Usuario registrado correctamente";
        }
    }

    @Override
    public String modificar(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getIdUsuario() == null || !usuarioRepository.existsById(usuarioDTO.getIdUsuario())) {
            return "El usuario no existe";
        }

        Usuario usuario = usuarioRepository.findById(usuarioDTO.getIdUsuario()).orElse(null);
        usuario.setCodigo((usuarioDTO.getCodigo() != null && !usuarioDTO.getCodigo().isBlank())
                        ? usuarioDTO.getCodigo() : usuario.getCodigo());

        usuario.setContrasena((usuarioDTO.getContrasena() != null && !usuarioDTO.getContrasena().isBlank())
                        ? usuarioDTO.getContrasena() : usuario.getContrasena());

        if (!usuario.getCodigo().matches("[0-9]{8}")) {
            return "Ingrese su DNI correctamente";
        }

        usuarioRepository.save(usuario);
        return "Usuario modificado correctamente";
    }


    @Override @Transactional
    public String eliminar(String codigoUsuario) {
        if (codigoUsuario.isBlank()) {
            return "Seleccione un usuario";
        }
        else if (!codigoUsuario.matches("[0-9]{8}")) {
            return "Ingrese su DNI correctamente";
        }
        else if (!usuarioRepository.existsByCodigo(codigoUsuario)) {
            return "El usuario no existe";
        }
        else {
            usuarioRepository.deleteByCodigo(codigoUsuario);
            return "Usuario eliminado correctamente";
        }
    }

    @Override
    public UsuarioDTO buscarPorCodigo(String codigoUsuario) {
        Usuario usuario = usuarioRepository.findByCodigo(codigoUsuario);
        if (usuario == null) {
            return null;
        }
        else  {
            return modelMapper.map(usuario, UsuarioDTO.class);
        }
    }


    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO buscarPorId(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario).map(usuario -> modelMapper.map(usuario, UsuarioDTO.class)).orElse(null);
    }
}
