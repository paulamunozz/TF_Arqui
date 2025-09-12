package com.upc.ecocycle.repositories;

import com.upc.ecocycle.enitites.Evento;
import com.upc.ecocycle.enitites.EventoXVecino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoXVecinoRepository extends JpaRepository<EventoXVecino, Integer> {
    List<EventoXVecino> findAllByEvento(Evento evento);
}
