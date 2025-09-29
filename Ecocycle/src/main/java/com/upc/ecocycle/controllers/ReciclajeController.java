package com.upc.ecocycle.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.upc.ecocycle.dto.CantidadReciclajeDTO;
import com.upc.ecocycle.dto.ReciclajeDTO;
import com.upc.ecocycle.services.EventoService;
import com.upc.ecocycle.services.MunicipalidadService;
import com.upc.ecocycle.services.ReciclajeService;
import com.upc.ecocycle.services.VecinoService;
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
    @Autowired
    private VecinoService vecinoService;
    @Autowired
    private MunicipalidadService municipalidadService;
    @Autowired
    private EventoService eventoService;

    @PostMapping("ecocycle/reciclaje/registrar")
    public ReciclajeDTO registrar(@RequestBody @Validated(Create.class) ReciclajeDTO reciclajeDTO) {

        ReciclajeDTO reciclaje = reciclajeService.registrar(reciclajeDTO);

        vecinoService.actualizacionPuntos(reciclaje.getVecinoId());
        vecinoService.calcularPuestos();
        municipalidadService.actualizacionPuntos(vecinoService.buscarPorId(reciclaje.getVecinoId()).getDistrito());
        municipalidadService.calcularPuestos();
        eventoService.actualizarPesoActual();
        return reciclaje;
    }

    @PutMapping("ecocycle/reciclaje/modificar")
    public ReciclajeDTO modificar(@RequestBody @Validated(Update.class) ReciclajeDTO reciclajeDTO) {
        ReciclajeDTO reciclaje = reciclajeService.modificar(reciclajeDTO);

        vecinoService.actualizacionPuntos(reciclaje.getVecinoId());
        vecinoService.calcularPuestos();
        municipalidadService.actualizacionPuntos(vecinoService.buscarPorId(reciclaje.getVecinoId()).getDistrito());
        municipalidadService.calcularPuestos();
        eventoService.actualizarPesoActual();
        return reciclaje;
    }

    @DeleteMapping("ecocycle/reciclaje/eliminar")
    public String eliminar(@RequestBody Integer idReciclaje) {
        ReciclajeDTO reciclaje = reciclajeService.buscarPorId(idReciclaje);

        vecinoService.actualizacionPuntos(reciclaje.getVecinoId());
        vecinoService.calcularPuestos();
        municipalidadService.actualizacionPuntos(vecinoService.buscarPorId(reciclaje.getVecinoId()).getDistrito());
        municipalidadService.calcularPuestos();
        eventoService.actualizarPesoActual();

        return reciclajeService.eliminar(idReciclaje);
    }

    @GetMapping("ecocycle/reciclaje/listarPorVecino")
    public List<ReciclajeDTO> listarReciclajeVecino(@RequestBody Integer vecinoId) {
        return reciclajeService.listarReciclajePorVecino(vecinoId);
    }

    @GetMapping("ecocycle/reciclaje/listarReciclajeFiltrado")
    public List<ReciclajeDTO> listarReciclajeFiltrado(@RequestBody JsonNode filtros) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String distrito = filtros.has("distrito") ? filtros.get("distrito").asText() : null;
        String tipo = filtros.has("tipo") ? filtros.get("tipo").asText() : null;
        String metodo = filtros.has("metodo") ? filtros.get("metodo").asText() : null;
        LocalDate fechaInicio = filtros.has("fechaInicio") ? LocalDate.parse(filtros.get("fechaInicio").asText(), formatter) : LocalDate.MIN;
        LocalDate fechaFin = filtros.has("fechaFin") ? LocalDate.parse(filtros.get("fechaFin").asText(), formatter) : LocalDate.MAX;
        String genero = filtros.has("genero") ? filtros.get("genero").asText() : null;
        Integer edadMin = filtros.has("edadMin") ? filtros.get("edadMin").asInt() : 0;
        Integer edadMax = filtros.has("edadMax") ? filtros.get("edadMax").asInt() : 200;
        return reciclajeService.listarReciclajeFiltrado(distrito, tipo, metodo, fechaInicio, fechaFin, genero, edadMin, edadMax);
    }

    @GetMapping("ecocycle/reciclaje/listarCantidadXTipo")
    public List<CantidadReciclajeDTO> listarCantidadReciclaje(@RequestBody(required = false) String distrito) {
        return reciclajeService.listarCantidadReciclaje(distrito);
    }
}
