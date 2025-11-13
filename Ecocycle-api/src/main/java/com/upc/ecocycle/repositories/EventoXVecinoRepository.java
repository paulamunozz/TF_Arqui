package com.upc.ecocycle.repositories;

import com.upc.ecocycle.dto.EventoXVecinoDTO;
import com.upc.ecocycle.dto.funcionalidades.CantidadesVecinosPorEvento;
import com.upc.ecocycle.dto.funcionalidades.ComentariosEventoDTO;
import com.upc.ecocycle.enitites.EventoXVecino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface EventoXVecinoRepository extends JpaRepository<EventoXVecino, Integer> {
    List<EventoXVecino> findAllByEvento_Id(Integer idEventoId);

    List<EventoXVecino> findAllByVecino_Id(Integer vecinoId);

    boolean existsByEvento_IdAndVecino_Id(Integer eventoId, Integer vecinoId);

    void deleteAllByEvento_Id(Integer eventoId);

    List<EventoXVecino> findAllByVecino_IdAndEventoTipoAndEventoMetodo(int vecinoId, String tipo, String metodo);

    @Query("SELECT new com.upc.ecocycle.dto.funcionalidades.ComentariosEventoDTO(exv.id, exv.vecino.nombre, exv.comentario)" +
            "FROM EventoXVecino exv WHERE exv.evento.id =:idEvento AND exv.comentario IS NOT NULL")
    List<ComentariosEventoDTO> comentariosEvento(@Param("idEvento") Integer idEvento);

    @Query("SELECT new com.upc.ecocycle.dto.funcionalidades.CantidadesVecinosPorEvento(" +
            "SUM(CASE WHEN exv.vecino.genero = 'F' THEN 1 ELSE 0 END)," +
            "SUM(CASE WHEN exv.vecino.genero = 'M' THEN 1 ELSE 0 END)," +
            "SUM(CASE WHEN exv.vecino.edad >= 15 AND exv.vecino.edad <= 24 THEN 1 ELSE 0 END)," +
            "SUM(CASE WHEN exv.vecino.edad >= 25 AND exv.vecino.edad <= 34 THEN 1 ELSE 0 END)," +
            "SUM(CASE WHEN exv.vecino.edad >= 35 AND exv.vecino.edad <= 44 THEN 1 ELSE 0 END)," +
            "SUM(CASE WHEN exv.vecino.edad >= 45 AND exv.vecino.edad <= 54 THEN 1 ELSE 0 END)," +
            "SUM(CASE WHEN exv.vecino.edad >= 55 AND exv.vecino.edad <= 64 THEN 1 ELSE 0 END)," +
            "SUM(CASE WHEN exv.vecino.edad >= 65 THEN 1 ELSE 0 END))" +
            "FROM EventoXVecino exv WHERE exv.evento.id =:idEvento")
    CantidadesVecinosPorEvento cantidadesVecinosPorEvento(@Param("idEvento") Integer idEvento);

    EventoXVecino findAllByEvento_IdAndVecino_Id(Integer eventoId, Integer vecinoId);

    List<EventoXVecino> findAllByVecino_IdAndVecino_Distrito(Integer idVecino, String distrito);

    Collection<Object> findAllByVecino_IdAndEvento_Municipalidad_Distrito(Integer idVecino, String distrito);
}