package com.upc.ecocycle.enitites;

import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "genero", nullable = false, length = 1)
    private String genero;

    @Column(name = "edad", nullable = false)
    private Integer edad;

    @Column(name = "distrito", nullable = false, length = 50)
    private String distrito;

    @Column(name = "direccion", nullable = false, length = 150)
    private String direccion;

    @Column(name = "puntajetotal", nullable = false)
    private Integer puntajetotal;

    @Column(name = "icono", nullable = false)
    private Integer icono;

    @Column(name = "puesto")
    private Integer puesto;

}