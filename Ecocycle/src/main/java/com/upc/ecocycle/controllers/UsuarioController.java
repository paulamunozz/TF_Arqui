package com.upc.ecocycle.controllers;

import com.upc.ecocycle.dto.UsuarioDTO;
import com.upc.ecocycle.services.UsuarioService;
import com.upc.ecocycle.validations.Create;
import com.upc.ecocycle.validations.Update;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/ecocycle/usuario/registrar")
    public String registrar(@RequestBody @Validated(Create.class) UsuarioDTO usuarioDTO) {
        return usuarioService.registrar(usuarioDTO);
    }

    @PutMapping("/ecocycle/usuario/modificar")
    public String modificar(@RequestBody @Validated(Update.class) UsuarioDTO usuarioDTO) {
        return usuarioService.modificar(usuarioDTO);
    }

    @DeleteMapping("/ecocycle/usuario/eliminar")
    public String eliminar(@RequestBody Integer idUsuario) {
        return usuarioService.eliminar(idUsuario);
    }

    @GetMapping("/ecocycle/usuario/buscarXcodigo")
    public UsuarioDTO buscarPorCodigo(@RequestBody String codigoUsuario) {
        return usuarioService.buscarPorCodigo(codigoUsuario);
    }

    @GetMapping("/ecocycle/usuario/buscarXid")
    public UsuarioDTO buscarPorId(@RequestBody Integer idUsuario) {
        return usuarioService.buscarPorId(idUsuario);
    }

    @GetMapping("/ecocycle/usuario/listar")
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }
}