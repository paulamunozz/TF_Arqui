package com.upc.ecocycle.controllers;

import com.upc.ecocycle.dto.funcionalidades.ComentariosEventoDTO;
import com.upc.ecocycle.dto.EventoXVecinoDTO;
import com.upc.ecocycle.services.EventoXVecinoService;
import com.upc.ecocycle.validations.Create;
import com.upc.ecocycle.validations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping
public class EventoXVecinoController {
    @Autowired private EventoXVecinoService eventoXVecinoService;

    @PostMapping("/ecocycle/exv/registrar")
    @PreAuthorize("hasRole('VECINO')")
    public String registrar(@RequestBody @Validated(Create.class) EventoXVecinoDTO eventoXVecinoDTO) {
        return eventoXVecinoService.registrar(eventoXVecinoDTO);
    }

    @PutMapping("/ecocycle/exv/modificar")
    @PreAuthorize("hasAnyRole('MUNICIPALIDAD', 'VECINO')")
    public String modificar(@RequestBody @Validated(Update.class) EventoXVecinoDTO eventoXVecinoDTO) {
        return eventoXVecinoService.modificar(eventoXVecinoDTO);
    }

    @DeleteMapping("/ecocycle/exv/eliminar")
    @PreAuthorize("hasRole('VECINO')")
    public String eliminar(@RequestBody Integer idEXV) {
        return eventoXVecinoService.eliminar(idEXV);
    }

    @GetMapping("/ecocycle/exv/comentariosEvento")
    @PreAuthorize("hasAnyRole('MUNICIPALIDAD', 'VECINO')")
    public List<ComentariosEventoDTO> comentariosEvento(@RequestBody Integer eventoId) {
        return eventoXVecinoService.comentariosEvento(eventoId);
    }
}
