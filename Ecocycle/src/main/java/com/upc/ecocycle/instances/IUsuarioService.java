package com.upc.ecocycle.instances;

import com.upc.ecocycle.dto.UsuarioDTO;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    String registrar(UsuarioDTO usuarioDTO);
    String modificar(UsuarioDTO usuarioDTO);
    String eliminar(Integer usuarioId);
    UsuarioDTO buscarPorCodigo(String codigoUsuario);
    UsuarioDTO buscarPorId(Integer idUsuario);
    List<UsuarioDTO> listarUsuarios();
}
