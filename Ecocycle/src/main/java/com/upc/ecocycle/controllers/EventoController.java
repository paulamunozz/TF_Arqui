package com.upc.ecocycle.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.upc.ecocycle.dto.EventoDTO;
import com.upc.ecocycle.dto.funcionalidades.CantidadEventosLogradosDTO;
import com.upc.ecocycle.services.EventoService;
import com.upc.ecocycle.validations.Create;
import com.upc.ecocycle.validations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        eventoService.actualizarPesoActual();
        return eventoService.buscarPorId(idEvento);
    }

    @GetMapping("/ecocycle/evento/listarYfiltrar")
    public List<EventoDTO> listarEventos(@RequestBody JsonNode filtros) {
        eventoService.actualizarPesoActual();

        String distrito = (filtros.has("distrito") && !filtros.get("distrito").isNull()
                && !filtros.get("distrito").asText().equalsIgnoreCase("null"))
                ? filtros.get("distrito").asText() : null;

        String nombre = (filtros.has("nombre") && !filtros.get("nombre").isNull()
                && !filtros.get("nombre").asText().equalsIgnoreCase("null"))
                ? filtros.get("nombre").asText() : null;

        String tipo = (filtros.has("tipo") && !filtros.get("tipo").isNull()
                && !filtros.get("tipo").asText().equalsIgnoreCase("null"))
                ? filtros.get("tipo").asText() : null;

        String metodo = (filtros.has("metodo") && !filtros.get("metodo").isNull()
                && !filtros.get("metodo").asText().equalsIgnoreCase("null"))
                ? filtros.get("metodo").asText() : null;

        LocalDate fechaInicio = (filtros.has("fechaInicio") && !filtros.get("fechaInicio").isNull()
                && !filtros.get("fechaInicio").asText().isBlank()
                && !filtros.get("fechaInicio").asText().equalsIgnoreCase("null"))
                ? LocalDate.parse(filtros.get("fechaInicio").asText()) : null;

        LocalDate fechaFin = (filtros.has("fechaFin") && !filtros.get("fechaFin").isNull()
                && !filtros.get("fechaFin").asText().isBlank()
                && !filtros.get("fechaFin").asText().equalsIgnoreCase("null"))
                ? LocalDate.parse(filtros.get("fechaFin").asText()) : null;

        return eventoService.listarEventos(distrito, nombre, tipo, metodo, fechaInicio, fechaFin);
    }

    @GetMapping("/ecocycle/evento/listarPorVecino")
    public List<EventoDTO> listarEventosPorVecino(@RequestBody JsonNode filtros){
        eventoService.actualizarPesoActual();

        Integer vecinoId = filtros.get("vecinoId").asInt();

        String nombre = (filtros.has("nombre") && !filtros.get("nombre").isNull()
                && !filtros.get("nombre").asText().equalsIgnoreCase("null"))
                ? filtros.get("nombre").asText() : null;

        String tipo = (filtros.has("tipo") && !filtros.get("tipo").isNull()
                && !filtros.get("tipo").asText().equalsIgnoreCase("null"))
                ? filtros.get("tipo").asText() : null;

        String metodo = (filtros.has("metodo") && !filtros.get("metodo").isNull()
                && !filtros.get("metodo").asText().equalsIgnoreCase("null"))
                ? filtros.get("metodo").asText() : null;

        LocalDate fechaInicio = (filtros.has("fechaInicio") && !filtros.get("fechaInicio").isNull()
                && !filtros.get("fechaInicio").asText().isBlank()
                && !filtros.get("fechaInicio").asText().equalsIgnoreCase("null"))
                ? LocalDate.parse(filtros.get("fechaInicio").asText()) : null;

        LocalDate fechaFin = (filtros.has("fechaFin") && !filtros.get("fechaFin").isNull()
                && !filtros.get("fechaFin").asText().isBlank()
                && !filtros.get("fechaFin").asText().equalsIgnoreCase("null"))
                ? LocalDate.parse(filtros.get("fechaFin").asText()) : null;

        return eventoService.listarEventosPorVecino(vecinoId, nombre, tipo, metodo, fechaInicio, fechaFin);
    }

    @GetMapping("/ecocycle/evento/cantidadEventosLogrados")
    public CantidadEventosLogradosDTO cantidadEventosLogrados(@RequestBody(required = false) String distrito) {
        eventoService.actualizarPesoActual();
        return eventoService.cantidadEventosLogrados(distrito);
    }
}
