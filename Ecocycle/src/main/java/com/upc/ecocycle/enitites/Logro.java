package com.upc.ecocycle.enitites;

import com.upc.ecocycle.validations.Create;
import com.upc.ecocycle.validations.Update;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "logro")
public class Logro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_logro", nullable = false)
    private Integer idLogro;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vecino", nullable = false)
    private Vecino vecino;

    @Size(max = 50)
    private String nombre;

}