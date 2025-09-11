package com.upc.ecocycle.enitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "evento_x_vecino")
public class EventoXVecino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exv", nullable = false)
    private Integer idEXV;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento eventoId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vecino", nullable = false)
    private Vecino vecinoId;

    @Column(name = "comentario", nullable = false)
    private Integer comentario;

}