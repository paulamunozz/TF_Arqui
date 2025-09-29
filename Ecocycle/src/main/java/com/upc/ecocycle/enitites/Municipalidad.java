package com.upc.ecocycle.enitites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "municipalidad")
public class Municipalidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_municipalidad", nullable = false)
    private Integer id;

    @Size(max = 8)
    @NotNull
    @Column(name = "codigo", nullable = false, length = 8, unique = true)
    private String codigo;

    @Size(max = 50)
    @NotNull
    @Column(name = "contrasena", nullable = false, length = 50)
    private String contrasena;

    @Size(max = 50)
    @NotNull
    @Column(name = "distrito", nullable = false, length = 50, unique = true)
    private String distrito;

    @NotNull
    @Column(name = "puntajetotal", nullable = false)
    private Integer puntajetotal;

    @NotNull
    @Column(name = "puesto", nullable = false)
    private Integer puesto;

}