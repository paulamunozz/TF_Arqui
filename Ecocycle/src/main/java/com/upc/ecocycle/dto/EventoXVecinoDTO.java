package com.upc.ecocycle.dto;

import com.upc.ecocycle.enitites.Evento;
import com.upc.ecocycle.enitites.Vecino;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class EventoXVecinoDTO {
    private Integer idEXV;

    private Evento eventoId;
    
    private Vecino vecinoId;

    @Column(name = "comentario", nullable = false)
    private Integer comentario;
}
