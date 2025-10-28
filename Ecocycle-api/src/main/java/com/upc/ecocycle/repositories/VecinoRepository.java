package com.upc.ecocycle.repositories;

import com.upc.ecocycle.dto.funcionalidades.CantidadesVecinosPorEvento;
import com.upc.ecocycle.enitites.Vecino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VecinoRepository extends JpaRepository<Vecino, Integer> {
    @Query("SELECT v FROM Vecino v " +
            "WHERE (:distrito IS NULL OR v.distrito = :distrito) " +
            "AND (:genero IS NULL OR v.genero = :genero) " +
            "AND (:edadMin IS NULL OR v.edad >= :edadMin) " +
            "AND (:edadMax IS NULL OR v.edad <= :edadMax) " +
            "AND (v.eliminado = false)" +
            "ORDER BY v.puntajetotal DESC")
    List<Vecino> findRankingFiltrado(String distrito, String genero, Integer edadMin, Integer edadMax);

    @Query("SELECT SUM(v.puntajetotal) " +
            "FROM Vecino v " +
            "WHERE v.distrito = :distrito")
    Integer sumPuntosByMunicipalidad(String distrito);

    boolean existsByIdAndEliminado(Integer id, Boolean eliminado);

    boolean existsByUser_UsernameAndEliminado(String userUsername, Boolean eliminado);

    Vecino findByUser_Username(String userUsername);
}
