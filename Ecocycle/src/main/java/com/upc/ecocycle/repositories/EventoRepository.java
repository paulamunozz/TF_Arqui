package com.upc.ecocycle.repositories;

import com.upc.ecocycle.dto.funcionalidades.CantidadEventosLogradosDTO;
import com.upc.ecocycle.enitites.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    boolean existsByNombre(String nombre);

    @Query("SELECT new com.upc.ecocycle.dto.funcionalidades.CantidadEventosLogradosDTO( " +
            "COUNT (CASE WHEN e.situacion = true THEN 1 END), " +
            "COUNT (CASE WHEN e.situacion = false THEN 1 END)) " +
            "FROM Evento e WHERE(:distrito IS NULL OR e.municipalidad.distrito = :distrito)")
    CantidadEventosLogradosDTO cantidadEventosLogrados(@Param("distrito") String distrito);
}
