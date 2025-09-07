package com.upc.ecocycle.controllers;

import com.upc.ecocycle.dto.UsuarioDTO;
import com.upc.ecocycle.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/ecocycle/usuario/registrar")
    public UsuarioDTO registrar(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.registrar(usuarioDTO);
    }
}
