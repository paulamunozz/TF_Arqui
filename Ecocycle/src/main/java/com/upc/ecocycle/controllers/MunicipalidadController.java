package com.upc.ecocycle.controllers;

import com.upc.ecocycle.dto.MunicipalidadDTO;
import com.upc.ecocycle.services.MunicipalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping
public class MunicipalidadController {
    @Autowired MunicipalidadService municipalidadService;

    @GetMapping("/ecocycle/municipalidad/buscarXid")
    public MunicipalidadDTO buscarPorId(@RequestBody Integer idUsuario) {
        municipalidadService.calcularPuestos();
        return municipalidadService.buscarPorId(idUsuario);
    }

    @GetMapping("/ecocycle/municipalidad/buscarXcodigo")
    public MunicipalidadDTO buscarPorCodigo(@RequestBody String codigoUsuario) {
        municipalidadService.calcularPuestos();
        return municipalidadService.buscarPorCodigo(codigoUsuario);
    }

    @GetMapping("/ecocycle/municipalidad/listar")
    public List<MunicipalidadDTO> listarMunicipalidades() {
        municipalidadService.calcularPuestos();
        return municipalidadService.listarMunicipalidades();
    }

    @GetMapping("/ecocycle/municipalidad/ranking")
    public List<MunicipalidadDTO> rankingMunicipalidades() {
        municipalidadService.calcularPuestos();
        return  municipalidadService.rankingMunicipalidades();
    }
}
