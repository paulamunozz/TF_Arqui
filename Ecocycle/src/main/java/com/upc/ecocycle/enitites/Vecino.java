package com.upc.ecocycle.enitites;

import com.upc.ecocycle.security.entities.User;
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

    @NotNull
    @Column(name = "puntajetotal", nullable = false)
    private Integer puntajetotal;

    @NotNull
    @Column(name = "icono", nullable = false)
    private Integer icono;

    @NotNull
    @Column(name = "puesto", nullable = false)
    private Integer puesto;

    @NotNull
    @Column(name = "eliminado", nullable = false)
    private Boolean eliminado = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}