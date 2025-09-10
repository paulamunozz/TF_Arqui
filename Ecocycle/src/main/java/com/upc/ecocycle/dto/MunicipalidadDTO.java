package com.upc.ecocycle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class MunicipalidadDTO {
    private Integer idMunicipalidad;
    private Integer usuarioId;
    private String distrito;
    private Integer puntajetotal;
    private Integer puesto;
}
