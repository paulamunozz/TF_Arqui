package com.upc.ecocycle.instances;

import com.upc.ecocycle.dto.UsuarioDTO;

import java.util.List;

public interface IUsuarioService {
    public String registrar(UsuarioDTO usuarioDTO);
    public String modificar(UsuarioDTO usuarioDTO);
    public String eliminar(String codigo);
    public UsuarioDTO buscarPorCodigo(String codigoUsuario);
    public List<UsuarioDTO> listarUsuarios();
}
