package com.upc.ecocycle.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.upc.ecocycle.dto.funcionalidades.CantidadReciclajeDTO;
import com.upc.ecocycle.dto.ReciclajeDTO;
import com.upc.ecocycle.services.EventoService;
import com.upc.ecocycle.services.MunicipalidadService;
import com.upc.ecocycle.services.ReciclajeService;
import com.upc.ecocycle.services.VecinoService;
import com.upc.ecocycle.validations.Create;
import com.upc.ecocycle.validations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController @RequestMapping("/ecocycle/reciclaje")
public class ReciclajeController {
    @Autowired
    private ReciclajeService reciclajeService;
    @Autowired
    private VecinoService vecinoService;
    @Autowired
    private MunicipalidadService municipalidadService;
    @Autowired
    private EventoService eventoService;

    @PostMapping("/registrar")
    @PreAuthorize("hasRole('VECINO')")
    public ReciclajeDTO registrar(@RequestBody @Validated(Create.class) ReciclajeDTO reciclajeDTO) {

        ReciclajeDTO reciclaje = reciclajeService.registrar(reciclajeDTO);

        vecinoService.actualizacionPuntos(reciclaje.getVecinoId());
        vecinoService.calcularPuestos();
        municipalidadService.actualizacionPuntos(vecinoService.buscarPorId(reciclaje.getVecinoId()).getDistrito());
        municipalidadService.calcularPuestos();
        eventoService.actualizarPesoActual();
        return reciclaje;
    }

    @PutMapping("/modificar")
    @PreAuthorize("hasRole('VECINO')")
    public ReciclajeDTO modificar(@RequestBody @Validated(Update.class) ReciclajeDTO reciclajeDTO) {
        ReciclajeDTO reciclaje = reciclajeService.modificar(reciclajeDTO);

        vecinoService.actualizacionPuntos(reciclaje.getVecinoId());
        vecinoService.calcularPuestos();
        municipalidadService.actualizacionPuntos(vecinoService.buscarPorId(reciclaje.getVecinoId()).getDistrito());
        municipalidadService.calcularPuestos();
        eventoService.actualizarPesoActual();
        return reciclaje;
    }

    @DeleteMapping("/eliminar")
    @PreAuthorize("hasRole('VECINO')")
    public String eliminar(@RequestBody Integer idReciclaje) {
        ReciclajeDTO reciclaje = reciclajeService.buscarPorId(idReciclaje);

        vecinoService.actualizacionPuntos(reciclaje.getVecinoId());
        vecinoService.calcularPuestos();
        municipalidadService.actualizacionPuntos(vecinoService.buscarPorId(reciclaje.getVecinoId()).getDistrito());
        municipalidadService.calcularPuestos();
        eventoService.actualizarPesoActual();

        return reciclajeService.eliminar(idReciclaje);
    }

    @GetMapping("/vecino")
    @PreAuthorize("hasRole('VECINO')")
    public List<ReciclajeDTO> listarReciclajeVecino(@RequestBody JsonNode filtros) {
        Integer vecinoId = filtros.get("vecinoId").asInt();

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

        return reciclajeService.listarReciclajePorVecino(vecinoId,tipo, metodo, fechaInicio, fechaFin);
    }

    @GetMapping("/distrito")
    @PreAuthorize("hasRole('MUNICIPALIDAD')")
    public List<ReciclajeDTO> listarReciclajeFiltrado(@RequestBody JsonNode filtros) {
        String distrito = (filtros.has("distrito") && !filtros.get("distrito").isNull()
                && !filtros.get("distrito").asText().equalsIgnoreCase("null"))
                ? filtros.get("distrito").asText() : null;

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

        String genero = (filtros.has("genero") && !filtros.get("genero").isNull()
                && !filtros.get("genero").asText().equalsIgnoreCase("null"))
                ? filtros.get("genero").asText() : null;

        Integer edadMin = (filtros.has("edadMin") && !filtros.get("edadMin").isNull())
                ? filtros.get("edadMin").asInt() : null;

        Integer edadMax = (filtros.has("edadMax") && !filtros.get("edadMax").isNull())
                ? filtros.get("edadMax").asInt() : null;
        return reciclajeService.listarReciclajeFiltrado(distrito, tipo, metodo, fechaInicio, fechaFin, genero, edadMin, edadMax);
    }

    @PostMapping("/cantidadPorTipo")
    public List<CantidadReciclajeDTO> listarCantidadReciclaje(@RequestBody(required = false) String distrito) {
        return reciclajeService.listarCantidadReciclaje(distrito);
    }
}
