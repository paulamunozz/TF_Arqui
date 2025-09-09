package com.upc.ecocycle.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.upc.ecocycle.dto.VecinoDTO;
import com.upc.ecocycle.services.VecinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping
public class VecinoController {
    @Autowired private VecinoService vecinoService;

    @PostMapping("/ecocycle/vecino/registrar")
    public String registrar(@RequestBody VecinoDTO vecinoDTO) {
        return vecinoService.registrar(vecinoDTO);
    }

    @PutMapping("/ecocycle/vecino/modificar")
    public String modificar(@RequestBody VecinoDTO vecinoDTO) {
        return vecinoService.modificar(vecinoDTO);
    }

    @DeleteMapping("/ecocycle/vecino/eliminar")
    public String eliminar(@RequestBody Integer idVecino) {
        return vecinoService.eliminar(idVecino);
    }

    @GetMapping("/ecocycle/vecino/buscar")
    public VecinoDTO buscarPorCodigo(@RequestBody String codigoUsuario) {
        return vecinoService.buscarPorCodigo(codigoUsuario);
    }

    @GetMapping("/ecocycle/vecino/actualizarPuntos")
    public List<VecinoDTO> actualizacionPuntos(@RequestBody JsonNode datos) {
        Integer idVecino = datos.get("idVecino").asInt();
        Integer puntos = datos.get("puntos").asInt();
        vecinoService.actualizacionPuntos(idVecino, puntos);

        vecinoService.calcularPuestos();
        return vecinoService.rankingFiltrado(null, null, 0, 200);
    }

    @GetMapping("/ecocycle/vecino/listar")
    public List<VecinoDTO> listarVecinos() {
        vecinoService.calcularPuestos();
        return vecinoService.listarVecinos();
    }

    @GetMapping("/ecocycle/vecino/ranking")
    public List<VecinoDTO> rankingFiltrado(@RequestBody JsonNode filtros) {
        vecinoService.calcularPuestos();

        String distrito = filtros.has("distrito") ? filtros.get("distrito").asText(null) : null;
        String genero   = filtros.has("genero") ? filtros.get("genero").asText(null) : null;
        Integer edadMin = filtros.has("edadMin") ? filtros.get("edadMin").asInt() : 0;
        Integer edadMax = filtros.has("edadMax") ? filtros.get("edadMax").asInt() : 200;

        return vecinoService.rankingFiltrado(distrito, genero, edadMin, edadMax);
    }

}
