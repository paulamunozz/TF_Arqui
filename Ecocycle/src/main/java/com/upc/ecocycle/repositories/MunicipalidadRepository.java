package com.upc.ecocycle.repositories;

import com.upc.ecocycle.enitites.Municipalidad;
import com.upc.ecocycle.enitites.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MunicipalidadRepository extends JpaRepository<Municipalidad, Integer> {
    Municipalidad findByUsuario(Usuario usuario);
    Municipalidad findByDistrito(String distrito);

    @Query("SELECT m FROM Municipalidad m " + "ORDER BY m.puntajetotal DESC")
    List<Municipalidad> findRanking();
}
