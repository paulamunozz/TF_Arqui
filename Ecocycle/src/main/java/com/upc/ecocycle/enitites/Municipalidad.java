package com.upc.ecocycle.enitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "municipalidad")
public class Municipalidad {
    @Id
    @Column(name = "id_municipalidad", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_municipalidad", nullable = false)
    private Usuario usuario;

    @Column(name = "distrito", nullable = false, length = 50)
    private String distrito;

    @Column(name = "puntajetotal", nullable = false)
    private Integer puntajetotal;

}