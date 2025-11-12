package com.upc.ecocycle.controllers;

import com.upc.ecocycle.dto.funcionalidades.CantidadesVecinosPorEvento;
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

@RestController
@RequestMapping("/ecocycle/exv/")
public class EventoXVecinoController {
    @Autowired private EventoXVecinoService eventoXVecinoService;

    @PostMapping("/registrar")
    @PreAuthorize("hasRole('VECINO')")
    public EventoXVecinoDTO registrar(@RequestBody @Validated(Create.class) EventoXVecinoDTO eventoXVecinoDTO) {
        return eventoXVecinoService.registrar(eventoXVecinoDTO);
    }

    @PutMapping("/modificar")
    @PreAuthorize("hasAnyRole('MUNICIPALIDAD', 'VECINO')")
    public EventoXVecinoDTO modificar(@RequestBody @Validated(Update.class) EventoXVecinoDTO eventoXVecinoDTO) {
        return eventoXVecinoService.modificar(eventoXVecinoDTO);
    }

    @DeleteMapping("/eliminar/{idEXV}")
    @PreAuthorize("hasRole('VECINO')")
    public String eliminar(@PathVariable Integer idEXV) {
        return eventoXVecinoService.eliminar(idEXV);
    }

    @GetMapping("/buscarPorEventoYVecino/{idEvento}/{idVecino}")
    public EventoXVecinoDTO buscarPorEventoYVecino(@PathVariable Integer idEvento, @PathVariable Integer idVecino) {
        return eventoXVecinoService.buscarPorEventoYVecino(idEvento, idVecino);
    }

    @PostMapping("/comentarios")
    @PreAuthorize("hasAnyRole('MUNICIPALIDAD', 'VECINO')")
    public List<ComentariosEventoDTO> comentariosEvento(@RequestBody Integer eventoId) {
        return eventoXVecinoService.comentariosEvento(eventoId);
    }

    @PostMapping("/estadisticasVecinosPorEvento")
    @PreAuthorize("hasAnyRole('MUNICIPALIDAD')")
    public CantidadesVecinosPorEvento cantidadesVecinosPorEvento(@RequestBody Integer eventoId) {
        return eventoXVecinoService.cantidadesVecinosPorEvento(eventoId);
    }
}
