package com.upc.ecocycle.dto;

import com.upc.ecocycle.enitites.Reciclaje;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor
public class CantidadReciclajeDTO {
    private String tipo;
    private Long cantidad;
    private BigDecimal peso;

    public CantidadReciclajeDTO(String tipo, Long cantidad, BigDecimal pesoTotal) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.peso = pesoTotal;
    }
}
