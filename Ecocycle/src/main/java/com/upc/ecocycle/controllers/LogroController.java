package com.upc.ecocycle.controllers;

import com.upc.ecocycle.dto.LogroDTO;
import com.upc.ecocycle.services.LogroService;
import com.upc.ecocycle.validations.Create;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecocycle/logro")
public class LogroController {
    @Autowired private LogroService logroService;

    @PostMapping("/registrar")
    public String registrarLogro(@RequestBody @Validated(Create.class) LogroDTO logroDTO) {
        return logroService.registrarLogro(logroDTO);
    }

    @GetMapping("/listarPorVecino")
    public List<LogroDTO> listarLogrosVecino(@RequestBody Integer vecinoId) {
        return logroService.listarLogrosPorVecino(vecinoId);
    }
}
