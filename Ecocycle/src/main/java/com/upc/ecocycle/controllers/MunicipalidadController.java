package com.upc.ecocycle.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.upc.ecocycle.dto.MunicipalidadDTO;
import com.upc.ecocycle.enitites.Municipalidad;
import com.upc.ecocycle.enitites.Usuario;
import com.upc.ecocycle.services.MunicipalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping
public class MunicipalidadController {
    @Autowired MunicipalidadService municipalidadService;

    @GetMapping("/ecocycle/municipalidad/buscarXid")
    public MunicipalidadDTO buscarPorId(@RequestBody Integer idUsuario) {
        return municipalidadService.buscarPorId(idUsuario);
    }

    @GetMapping("/ecocycle/municipalidad/buscarXcodigo")
    public MunicipalidadDTO buscarPorCodigo(@RequestBody String codigoUsuario) {
        return municipalidadService.buscarPorCodigo(codigoUsuario);
    }

    @GetMapping("/ecocycle/municipalidad/buscarXdistrito")
    public MunicipalidadDTO buscarPorDistrito(@RequestBody String distrito) {
        return municipalidadService.buscarPorDistrito(distrito);
    }

    @GetMapping("/ecocycle/municipalidad/listar")
    public List<MunicipalidadDTO> listarMunicipalidades() {
        return municipalidadService.listarMunicipalidades();
    }

    @GetMapping("/ecocycle/municipalidad/ranking")
    public List<MunicipalidadDTO> rankingMunicipalidades() {
        return  municipalidadService.rankingMunicipalidades();
    }
}
