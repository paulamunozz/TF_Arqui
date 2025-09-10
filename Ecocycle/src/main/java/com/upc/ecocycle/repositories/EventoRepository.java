package com.upc.ecocycle.repositories;

import com.upc.ecocycle.enitites.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    boolean existsByNombre(String nombre);
}
