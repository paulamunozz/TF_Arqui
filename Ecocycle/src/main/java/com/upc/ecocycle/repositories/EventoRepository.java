package com.upc.ecocycle.repositories;

import com.upc.ecocycle.dto.ComentariosEventoDTO;
import com.upc.ecocycle.enitites.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    boolean existsByNombre(String nombre);

    List<Evento> findAllByNombreContainingIgnoreCase(String nombre);
}
