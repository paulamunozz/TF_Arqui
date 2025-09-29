package com.upc.ecocycle.repositories;

import com.upc.ecocycle.enitites.EventoXVecino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoXVecinoRepository extends JpaRepository<EventoXVecino, Integer> {
    List<EventoXVecino> findAllByEvento_Id(Integer idEventoId);
    List<EventoXVecino> findAllByVecino_Id(Integer vecinoId);
    boolean existsByEvento_IdAndVecino_Id(Integer eventoId, Integer vecinoId);
    List<EventoXVecino> findAllByVecino_IdAndEventoTipoAndEventoMetodo(int vecinoId, String tipo, String metodo);
}