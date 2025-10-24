package com.upc.ecocycle.enitites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_municipalidad", nullable = false)
    private Municipalidad municipalidad;

    @Size(max = 50)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 50, unique = true)
    private String nombre;

    @Size(max = 500)
    @NotNull
    @Column(name = "descripcion", nullable = false, length = 300)
    private String descripcion;

    @NotNull
    @Column(name = "peso_objetivo", nullable = false, precision = 5, scale = 2)
    private BigDecimal pesoObjetivo;

    @NotNull
    @Column(name = "peso_actual", nullable = false, precision = 5, scale = 2)
    private BigDecimal pesoActual;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Size(max = 50)
    @NotNull
    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @Size(max = 50)
    @NotNull
    @Column(name = "metodo", nullable = false, length = 50)
    private String metodo;

    @NotNull
    @Column(name = "bonificacion", nullable = false)
    private Double bonificacion;

    @NotNull
    @Column(name = "situacion", nullable = false)
    private Boolean situacion = false;

}