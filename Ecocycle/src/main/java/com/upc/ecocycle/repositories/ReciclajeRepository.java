package com.upc.ecocycle.repositories;

import com.upc.ecocycle.dto.CantidadReciclajeDTO;
import com.upc.ecocycle.enitites.Reciclaje;
import com.upc.ecocycle.enitites.Vecino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReciclajeRepository extends JpaRepository<Reciclaje, Integer> {
    List<Reciclaje> findAllByVecino(Vecino vecino);

    @Query("SELECT new com.upc.ecocycle.dto.CantidadReciclajeDTO(" +
            "r.tipo, COUNT(r), SUM(r.peso))" +
            "FROM Reciclaje r " +
            "WHERE (:distrito IS NULL OR r.vecino.distrito = :distrito)"+
            "GROUP BY r.tipo")
    List<CantidadReciclajeDTO> listarCantidadReciclaje(@Param("distrito") String distrito);
}
