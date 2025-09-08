package com.upc.ecocycle.enitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer id;

    @Column(name = "codigo", nullable = false, length = 8)
    private String codigo;

    @Column(name = "contrasena", nullable = false, length = 50)
    private String contrasena;

}