package com.upc.ecocycle.enitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "evento")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_municipalidad", nullable = false)
    private Municipalidad idMunicipalidad;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 300)
    private String descripcion;

    @Column(name = "pesoobjetivo", nullable = false, precision = 5, scale = 2)
    private BigDecimal pesoobjetivo;

    @Column(name = "fechainicio", nullable = false)
    private LocalDate fechainicio;

    @Column(name = "fechafin", nullable = false)
    private LocalDate fechafin;

}