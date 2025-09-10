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
    private Integer idMunicipalidad;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Size(max = 50)
    @NotNull
    @Column(name = "distrito", nullable = false, length = 50)
    private String distrito;

    @NotNull
    @Column(name = "puntajetotal", nullable = false)
    private Integer puntajetotal;

    @Column(name = "puesto")
    private Integer puesto;

}