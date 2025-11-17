package com.upc.ecocycle.dto.funcionalidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CantidadReciclajeDTO {
    private String tipo;
    private Long cantidad;
    private BigDecimal peso;
}
