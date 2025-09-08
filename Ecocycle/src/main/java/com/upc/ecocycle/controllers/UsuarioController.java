package com.upc.ecocycle.controllers;

import com.upc.ecocycle.dto.UsuarioDTO;
import com.upc.ecocycle.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/ecocycle/usuario/registrar")
    public String registrar(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.registrar(usuarioDTO);
    }

    @PutMapping("/ecocycle/usuario/modificar")
    public String modificar(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.modificar(usuarioDTO);
    }

    @DeleteMapping("/ecocycle/usuario/eliminar")
    public String eliminar(@RequestBody String codigoUsuario) {
        return usuarioService.eliminar(codigoUsuario);
    }

    @GetMapping("/ecocycle/usuario/buscar")
    public UsuarioDTO buscarPorCodigo(@RequestBody String codigoUsuario) {
        return usuarioService.buscarPorCodigo(codigoUsuario);
    }


    @GetMapping("/ecocycle/usuario/listar")
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }
}