package com.upc.ecocycle.repositories;

import com.upc.ecocycle.enitites.Evento;
import com.upc.ecocycle.enitites.EventoXVecino;
import com.upc.ecocycle.enitites.Vecino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoXVecinoRepository extends JpaRepository<EventoXVecino, Integer> {
    List<EventoXVecino> findAllByEvento(Evento evento);
    List<EventoXVecino> findAllByVecino(Vecino vecino);
    boolean existsByEventoAndVecino(Evento evento, Vecino vecino);
    EventoXVecino findByVecinoIdAndEventoTipoAndEventoMetodo(Integer vecinoId, String tipo, String metodo);
    List<Integer> findVecinoIdsByEventoIdEvento(Integer idEvento);
}
