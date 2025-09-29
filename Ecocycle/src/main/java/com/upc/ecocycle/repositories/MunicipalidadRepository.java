package com.upc.ecocycle.repositories;

import com.upc.ecocycle.enitites.Municipalidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MunicipalidadRepository extends JpaRepository<Municipalidad, Integer> {
    boolean existsByDistritoIgnoreCase(String distrito);
    Municipalidad findByDistritoIgnoreCase(String distrito);
    @Query("SELECT m FROM Municipalidad m " + "ORDER BY m.puntajetotal DESC")
    List<Municipalidad> findRanking();

    Municipalidad findByCodigo(String codigo);
}
