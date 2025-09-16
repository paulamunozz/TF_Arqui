package com.upc.ecocycle.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.upc.ecocycle.dto.CantidadReciclajeDTO;
import com.upc.ecocycle.dto.ReciclajeDTO;
import com.upc.ecocycle.services.ReciclajeService;
import com.upc.ecocycle.validations.Create;
import com.upc.ecocycle.validations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController @RequestMapping
public class ReciclajeController {
    @Autowired
    private ReciclajeService reciclajeService;

    @PostMapping("ecocycle/reciclaje/registrar")
    public String registrar(@RequestBody @Validated(Create.class) ReciclajeDTO reciclajeDTO) {
        return reciclajeService.registrar(reciclajeDTO);
    }

    @PutMapping("ecocycle/reciclaje/modificar")
    public String modificar(@RequestBody @Validated(Update.class) ReciclajeDTO reciclajeDTO) {
        return reciclajeService.modificar(reciclajeDTO);
    }

    @DeleteMapping("ecocycle/reciclaje/eliminar")
    public String eliminar(@RequestBody Integer idReciclaje) {
        return reciclajeService.eliminar(idReciclaje);
    }

    @GetMapping("ecocycle/reciclaje/listarPorVecino")
    public List<ReciclajeDTO> listarReciclajeVecino(@RequestBody Integer vecinoId) {
        return reciclajeService.listarReciclajeVecino(vecinoId);
    }

    @GetMapping("ecocycle/reciclaje/listarPorDistrito")
    public List<ReciclajeDTO> listarReciclajeDistrito(@RequestBody JsonNode filtros) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String distrito = filtros.has("distrito") ? filtros.get("distrito").asText() : null;
        String tipo = filtros.has("tipo") ? filtros.get("tipo").asText() : null;
        String metodo = filtros.has("metodo") ? filtros.get("metodo").asText() : null;
        LocalDate fechaInicio = filtros.has("fechaInicio") ? LocalDate.parse(filtros.get("fechaInicio").asText(), formatter) : LocalDate.MIN;
        LocalDate fechaFin = filtros.has("fechaFin") ? LocalDate.parse(filtros.get("fechaFin").asText(), formatter) : LocalDate.MAX;
        String genero = filtros.has("genero") ? filtros.get("genero").asText() : null;
        Integer edadMin = filtros.has("edadMin") ? filtros.get("edadMin").asInt() : 0;
        Integer edadMax = filtros.has("edadMax") ? filtros.get("edadMax").asInt() : 200;
        return reciclajeService.listarReciclajeDistrito(distrito, tipo, metodo, fechaInicio, fechaFin, genero, edadMin, edadMax);
    }

    @GetMapping("ecocycle/reciclaje/listarCantidadXTipo")
    public List<CantidadReciclajeDTO> listarCantidadReciclaje(@RequestBody(required = false) String distrito) {
        return reciclajeService.listarCantidadReciclaje(distrito);
    }
}
