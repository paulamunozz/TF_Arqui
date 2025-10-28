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
@Table(name = "municipalidad")
public class Municipalidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_municipalidad", nullable = false)
    private Integer id;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}