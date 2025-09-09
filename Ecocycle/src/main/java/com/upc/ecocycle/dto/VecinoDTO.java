package com.upc.ecocycle.dto;

import com.upc.ecocycle.enitites.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VecinoDTO {
    private Integer idVecino;
    private Integer usuarioId;
    private String nombre;
    private String genero;
    private String edad;
    private String distrito;
    private String direccion;
    private Integer puntajetotal;
    private Integer icono;
    private Integer puesto;
}
