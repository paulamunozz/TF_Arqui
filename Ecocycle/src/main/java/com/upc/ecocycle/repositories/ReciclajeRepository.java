package com.upc.ecocycle.repositories;

import com.upc.ecocycle.dto.CantidadReciclajeDTO;
import com.upc.ecocycle.enitites.Reciclaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ReciclajeRepository extends JpaRepository<Reciclaje, Integer> {
    @Query("SELECT new com.upc.ecocycle.dto.CantidadReciclajeDTO(" +
            "r.tipo, COUNT(r), SUM(r.peso))" +
            "FROM Reciclaje r " +
            "WHERE (:distrito IS NULL OR r.vecino.distrito = :distrito)"+
            "GROUP BY r.tipo")
    List<CantidadReciclajeDTO> listarCantidadReciclaje(@Param("distrito") String distrito);

    @Query("SELECT COALESCE(SUM(r.peso), 0) " +
            "FROM Reciclaje r " +
            "JOIN EventoXVecino exv ON r.vecino.id = exv.vecino.id " +
            "WHERE exv.evento.id = :idEvento " +
            "AND r.tipo = exv.evento.tipo " +
            "AND r.metodo = exv.evento.metodo " +
            "AND r.fecha BETWEEN exv.evento.fechaInicio AND exv.evento.fechaFin")
    BigDecimal calcularPesoPorEvento(@Param("idEvento") Integer idEvento);

    @Query("SELECT COALESCE(SUM(r.puntaje), 0) FROM Reciclaje r WHERE r.vecino.id = :idVecino")
    Integer sumarPuntajePorVecino(@Param("idVecino") Integer idVecino);
    List<Reciclaje> findAllByVecinoId(Integer idVecino);
}
