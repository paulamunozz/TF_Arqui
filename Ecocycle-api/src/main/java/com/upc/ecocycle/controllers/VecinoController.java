package com.upc.ecocycle.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.upc.ecocycle.dto.VecinoDTO;
import com.upc.ecocycle.enitites.EventoXVecino;
import com.upc.ecocycle.enitites.Vecino;
import com.upc.ecocycle.services.VecinoService;
import com.upc.ecocycle.validations.Create;
import com.upc.ecocycle.validations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/ecocycle/vecino")
public class VecinoController {
    @Autowired private VecinoService vecinoService;

    @PostMapping("/registrar")
    public VecinoDTO registrar(@RequestBody @Validated(Create.class) VecinoDTO vecinoDTO) {
        VecinoDTO vecino = vecinoService.registrar(vecinoDTO);
        vecinoService.calcularPuestos();
        return vecino;
    }

    @PutMapping("/modificar")
    @PreAuthorize("hasRole('VECINO')")
    public VecinoDTO modificar(@RequestBody @Validated(Update.class) VecinoDTO vecinoDTO) {
        return vecinoService.modificar(vecinoDTO);
    }

    @DeleteMapping("/eliminar/{idVecino}")
    @PreAuthorize("hasRole('VECINO')")
    public String eliminar(@PathVariable Integer idVecino) {
        return vecinoService.eliminar(idVecino);
    }
    @PostMapping("/buscarPorDNI")
    @PreAuthorize("hasRole('VECINO')")
    public VecinoDTO buscarPorDni(@RequestBody String dni) {
        return vecinoService.buscarPorDni(dni);
    }

    @PostMapping("/buscarPorID")
    @PreAuthorize("hasRole('VECINO')")
    public VecinoDTO buscarPorId(@RequestBody Integer idVecino){
        vecinoService.actualizacionPuntos(idVecino);
        vecinoService.calcularPuestos();
        return vecinoService.buscarPorId(idVecino);
    }

    @PostMapping("/ranking")
    public List<VecinoDTO> rankingFiltrado(@RequestBody JsonNode filtros) {
        vecinoService.calcularPuestos();

        String distrito = (filtros.has("distrito") && !filtros.get("distrito").isNull()
                && !filtros.get("distrito").asText().isBlank()
                && !filtros.get("distrito").asText().equalsIgnoreCase("null"))
                ? filtros.get("distrito").asText() : null;

        String genero = (filtros.has("genero") && !filtros.get("genero").isNull()
                && !filtros.get("genero").asText().isBlank()
                && !filtros.get("genero").asText().equalsIgnoreCase("null"))
                ? filtros.get("genero").asText() : null;

        Integer edadMin = (filtros.has("edadMin") && !filtros.get("edadMin").isNull())
                && !filtros.get("edadMin").asText().isBlank()
                ? filtros.get("edadMin").asInt() : null;

        Integer edadMax = (filtros.has("edadMax") && !filtros.get("edadMax").isNull())
                && !filtros.get("edadMax").asText().isBlank()
                ? filtros.get("edadMax").asInt() : null;

        return vecinoService.rankingFiltrado(distrito, genero, edadMin, edadMax);
    }
}
