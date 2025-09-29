package com.upc.ecocycle.repositories;

import com.upc.ecocycle.enitites.Logro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogroRepository extends JpaRepository<Logro, Integer> {
    List<Logro> findAllByVecino_Id(Integer vecinoId);
}
