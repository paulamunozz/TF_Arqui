package com.upc.ecocycle.enitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "evento_x_vecino")
public class EventoXVecino {
    @EmbeddedId
    private EventoXVecinoId id;

    @MapsId("idEvento")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento idEvento;

    @MapsId("idVecino")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vecino", nullable = false)
    private Vecino idVecino;

    @Column(name = "comentario", nullable = false)
    private Integer comentario;

    @Column(name = "vecino_id_vecino", nullable = false)
    private Integer vecinoIdVecino;

    @Column(name = "evento_id_evento", nullable = false)
    private Integer eventoIdEvento;

}