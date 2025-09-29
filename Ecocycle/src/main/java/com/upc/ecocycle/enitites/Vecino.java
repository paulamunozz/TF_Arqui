package com.upc.ecocycle.enitites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vecino")
public class Vecino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vecino", nullable = false)
    private Integer id;

    @Size(max = 8)
    @NotNull
    @Column(name = "dni", nullable = false, length = 8, unique = true)
    private String dni;

    @Size(max = 50)
    @NotNull
    @Column(name = "contrasena", nullable = false, length = 50)
    private String contrasena;

    @Size(max = 100)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Size(max = 1)
    @NotNull
    @Column(name = "genero", nullable = false, length = 1)
    private String genero;

    @NotNull
    @Column(name = "edad", nullable = false)
    private Integer edad;

    @Size(max = 50)
    @NotNull
    @Column(name = "distrito", nullable = false, length = 50)
    private String distrito;

    @Size(max = 150)
    @NotNull
    @Column(name = "direccion", nullable = false, length = 150)
    private String direccion;

    @NotNull
    @Column(name = "puntajetotal", nullable = false)
    private Integer puntajetotal;

    @NotNull
    @Column(name = "icono", nullable = false)
    private Integer icono;

    @NotNull
    @Column(name = "puesto", nullable = false)
    private Integer puesto;

}