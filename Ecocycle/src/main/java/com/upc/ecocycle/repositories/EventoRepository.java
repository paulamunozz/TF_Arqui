package com.upc.ecocycle.repositories;

import com.upc.ecocycle.dto.EventoDTO;
import com.upc.ecocycle.enitites.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    boolean existsByNombre(String nombre);

    List<Evento> findAllByNombreContainingIgnoreCase(String nombre);
}
