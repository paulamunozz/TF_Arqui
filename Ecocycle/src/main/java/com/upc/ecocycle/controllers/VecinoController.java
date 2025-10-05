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
@RestController @RequestMapping
public class VecinoController {
    @Autowired private VecinoService vecinoService;

    @PostMapping("/ecocycle/vecino/registrar")
    public String registrar(@RequestBody @Validated(Create.class) VecinoDTO vecinoDTO) {
        String mensaje = vecinoService.registrar(vecinoDTO);
        vecinoService.calcularPuestos();
        return mensaje;
    }

    @PutMapping("/ecocycle/vecino/modificar")
    @PreAuthorize("hasRole('VECINO')")
    public String modificar(@RequestBody @Validated(Update.class) VecinoDTO vecinoDTO) {
        return vecinoService.modificar(vecinoDTO);
    }

    @DeleteMapping("/ecocycle/vecino/eliminar")
    @PreAuthorize("hasRole('VECINO')")
    public String eliminar(@RequestBody Integer idVecino) {
        return vecinoService.eliminar(idVecino);
    }

    @GetMapping("/ecocycle/vecino/buscarPorDNI")
    @PreAuthorize("hasRole('ADMIN')")
    public VecinoDTO buscarPorDni(@RequestBody String dni) {
        return vecinoService.buscarPorDni(dni);
    }

    @GetMapping("/ecocycle/vecino/buscarXid")
    @PreAuthorize("hasRole('VECINO')")
    public VecinoDTO buscarPorId(@RequestBody Integer idVecino){
        vecinoService.actualizacionPuntos(idVecino);
        return vecinoService.buscarPorId(idVecino);
    }

    @GetMapping("/ecocycle/vecino/listar")
    @PreAuthorize("hasRole('ADMIN')")
    public List<VecinoDTO> listarVecinos() {
        vecinoService.calcularPuestos();
        return vecinoService.listarVecinos();
    }

    @GetMapping("/ecocycle/vecino/listarVecinosPorEvento")
    @PreAuthorize("hasRole('MUNICIPALIDAD')")
    public List<VecinoDTO> listarVecinosPorEvento(@RequestBody Integer idEvento) {
        return vecinoService.listarVecinosPorEvento(idEvento);
    }

    @GetMapping("/ecocycle/vecino/ranking")
    public List<VecinoDTO> rankingFiltrado(@RequestBody JsonNode filtros) {
        vecinoService.calcularPuestos();

        String distrito = (filtros.has("distrito") && !filtros.get("distrito").isNull()
                && !filtros.get("distrito").asText().equalsIgnoreCase("null"))
                ? filtros.get("distrito").asText() : null;

        String genero = (filtros.has("genero") && !filtros.get("genero").isNull()
                && !filtros.get("genero").asText().equalsIgnoreCase("null"))
                ? filtros.get("genero").asText() : null;

        Integer edadMin = (filtros.has("edadMin") && !filtros.get("edadMin").isNull())
                ? filtros.get("edadMin").asInt() : null;

        Integer edadMax = (filtros.has("edadMax") && !filtros.get("edadMax").isNull())
                ? filtros.get("edadMax").asInt() : null;

        return vecinoService.rankingFiltrado(distrito, genero, edadMin, edadMax);
    }
}
