package com.upc.ecocycle.controllers;

import com.upc.ecocycle.dto.funcionalidades.ComentariosEventoDTO;
import com.upc.ecocycle.dto.EventoXVecinoDTO;
import com.upc.ecocycle.services.EventoXVecinoService;
import com.upc.ecocycle.validations.Create;
import com.upc.ecocycle.validations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecocycle/exv/")
public class EventoXVecinoController {
    @Autowired private EventoXVecinoService eventoXVecinoService;

    @PostMapping("/registrar")
    public EventoXVecinoDTO registrar(@RequestBody @Validated(Create.class) EventoXVecinoDTO eventoXVecinoDTO) {
        return eventoXVecinoService.registrar(eventoXVecinoDTO);
    }

    @PutMapping("/modificar")
    public EventoXVecinoDTO modificar(@RequestBody @Validated(Update.class) EventoXVecinoDTO eventoXVecinoDTO) {
        return eventoXVecinoService.modificar(eventoXVecinoDTO);
    }

    @DeleteMapping("/eliminar")
    public String eliminar(@RequestBody Integer idEXV) {
        return eventoXVecinoService.eliminar(idEXV);
    }

    @GetMapping("/comentariosEvento")
    public List<ComentariosEventoDTO> comentariosEvento(@RequestBody Integer eventoId) {
        return eventoXVecinoService.comentariosEvento(eventoId);
    }
}
