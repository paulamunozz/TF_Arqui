package com.upc.ecocycle.enitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "reciclaje")
public class Reciclaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reciclaje", nullable = false)
    private Integer idReciclaje;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vecino", nullable = false)
    private Vecino vecino;

    @Column(name = "peso", nullable = false, precision = 5, scale = 2)
    private BigDecimal peso;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "metodo", nullable = false, length = 50)
    private String metodo;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "puntaje", nullable = false)
    private Integer puntaje;
}