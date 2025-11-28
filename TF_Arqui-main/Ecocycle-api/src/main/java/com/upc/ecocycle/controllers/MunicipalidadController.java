package com.upc.ecocycle.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.upc.ecocycle.dto.MunicipalidadDTO;
import com.upc.ecocycle.enitites.Municipalidad;
import com.upc.ecocycle.security.entities.User;
import com.upc.ecocycle.services.MunicipalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/ecocycle/municipalidad")
public class MunicipalidadController {
    @Autowired MunicipalidadService municipalidadService;

    @PutMapping("/modificarContrasena")
    @PreAuthorize("hasRole('MUNICIPALIDAD')")
    public String modificarContrasena(@RequestBody JsonNode datos) {
        Integer id = datos.get("id").asInt();

        String contrasena = (datos.has("contrasena") && !datos.get("contrasena").isNull()
                && !datos.get("contrasena").asText().equalsIgnoreCase("null"))
                ? datos.get("contrasena").asText() : null;
        return  municipalidadService.modificarContrasena(id, contrasena);
    }

    @PostMapping("/buscarXid")
    @PreAuthorize("hasRole('MUNICIPALIDAD')")
    public MunicipalidadDTO buscarPorId(@RequestBody Integer idUsuario) {
        municipalidadService.calcularPuestos();
        return municipalidadService.buscarPorId(idUsuario);
    }

    @PostMapping("/buscarXcodigo")
    @PreAuthorize("hasRole('MUNICIPALIDAD')")
    public MunicipalidadDTO buscarPorCodigo(@RequestBody String codigoUsuario) {
        municipalidadService.calcularPuestos();
        return municipalidadService.buscarPorCodigo(codigoUsuario);
    }

    @GetMapping("/ranking")
    public List<MunicipalidadDTO> rankingMunicipalidades() {
        municipalidadService.calcularPuestos();
        return  municipalidadService.rankingMunicipalidades();
    }
}
