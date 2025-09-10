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
        if (usuarioRepository.existsByCodigo(usuarioDTO.getCodigo())) {
            return "El usuario ya existe";
        }

        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        usuarioRepository.save(usuario);
        return "Usuario registrado correctamente";
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

        usuarioRepository.save(usuario);
        return "Usuario modificado correctamente";
    }


    @Transactional
    @Override
    public String eliminar(Integer usuarioId) {
        if (usuarioId==null) {
            return "Seleccione un usuario";
        }
        else if (!usuarioRepository.existsById(usuarioId)) {
            return "El usuario no existe";
        }
        else {
            usuarioRepository.deleteById(usuarioId);
            return "Usuario eliminado correctamente";
        }
    }

    @Override
    public UsuarioDTO buscarPorCodigo(String codigoUsuario) {
        if (codigoUsuario.isBlank()) {
            return null;
        }

        Usuario usuario = usuarioRepository.findByCodigo(codigoUsuario);
        if (usuario == null) {
            return null;
        }
        else  {
            return modelMapper.map(usuario, UsuarioDTO.class);
        }
    }

    @Override
    public UsuarioDTO buscarPorId(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario).map(usuario -> modelMapper.map(usuario, UsuarioDTO.class)).orElse(null);
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }
}
