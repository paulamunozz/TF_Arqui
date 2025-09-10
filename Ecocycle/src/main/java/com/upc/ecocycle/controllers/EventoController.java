package com.upc.ecocycle.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.upc.ecocycle.dto.EventoDTO;
import com.upc.ecocycle.services.EventoService;
import com.upc.ecocycle.validations.Create;
import com.upc.ecocycle.validations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController @RequestMapping
public class EventoController {
    @Autowired
    EventoService eventoService;

    @PostMapping("/ecocycle/evento/registrar")
    public String registrar(@RequestBody @Validated(Create.class) EventoDTO eventoDTO) {
        return eventoService.registrar(eventoDTO);
    }

    @PutMapping("/ecocycle/evento/modificar")
    public String modificar(@RequestBody @Validated(Update.class) EventoDTO eventoDTO) {
        return eventoService.modificar(eventoDTO);
    }

    @DeleteMapping("/ecocycle/evento/eliminar")
    public String eliminar(@RequestBody Integer idEvento) {
        return eventoService.eliminar(idEvento);
    }

    @GetMapping("/ecocycle/evento/buscarXid")
    public EventoDTO buscarPorId(@RequestBody Integer idEvento) {
        return eventoService.buscarPorId(idEvento);
    }

    @GetMapping("/ecocycle/evento/listarYfiltrar")
    public List<EventoDTO> listarEventos(@RequestBody JsonNode filtros) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String nombre = filtros.has("nombre") ? filtros.get("nombre").asText() : null;
        String estado   = filtros.has("estado") ? filtros.get("estado").asText() : null;
        String distrito = filtros.has("distrito") ? filtros.get("distrito").asText() : null;
        LocalDate fechaInicio = filtros.has("fechaInicio") ? LocalDate.parse(filtros.get("fechaInicio").asText(), formatter) : null;
        LocalDate fechaFin = filtros.has("fechaFin") ? LocalDate.parse(filtros.get("fechaFin").asText(), formatter) : null;
        return eventoService.listarEventos(nombre, estado, distrito, fechaInicio, fechaFin);
    }
}
