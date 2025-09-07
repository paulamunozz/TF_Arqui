package com.upc.ecocycle.instances;

import com.upc.ecocycle.dto.UsuarioDTO;
import com.upc.ecocycle.enitites.Usuario;

import java.util.List;

public interface IUsuarioService {
    public UsuarioDTO registrar(UsuarioDTO usuarioDTO);
    public UsuarioDTO modificar(Usuario usuario);
    public void eliminar(Integer id);
    public UsuarioDTO buscarPorId(Integer id);
    public List<UsuarioDTO> listarUsuarios();
}
