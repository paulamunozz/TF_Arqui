package com.upc.ecocycle.repositories;

import com.upc.ecocycle.enitites.Logro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogroRepository extends JpaRepository<Logro, Integer> {
    List<Logro> findAllByVecino_Id(Integer vecinoId);

    boolean existsByVecino_IdAndNombre(Integer vecinoId, String nombre);

    boolean existsByVecino_Id(Integer vecinoId);
}
