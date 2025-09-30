package com.upc.ecocycle.repositories;

import com.upc.ecocycle.dto.ComentariosEventoDTO;
import com.upc.ecocycle.enitites.EventoXVecino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventoXVecinoRepository extends JpaRepository<EventoXVecino, Integer> {
    List<EventoXVecino> findAllByEvento_Id(Integer idEventoId);
    List<EventoXVecino> findAllByVecino_Id(Integer vecinoId);
    boolean existsByEvento_IdAndVecino_Id(Integer eventoId, Integer vecinoId);
    void deleteAllByEvento_Id(Integer eventoId);
    List<EventoXVecino> findAllByVecino_IdAndEventoTipoAndEventoMetodo(int vecinoId, String tipo, String metodo);
    @Query("SELECT new com.upc.ecocycle.dto.ComentariosEventoDTO(exv.vecino.nombre, exv.comentario)" +
            "FROM EventoXVecino exv WHERE exv.evento.id =:idEvento")
    List<ComentariosEventoDTO> comentariosEvento(@Param("idEvento") Integer idEvento);
}