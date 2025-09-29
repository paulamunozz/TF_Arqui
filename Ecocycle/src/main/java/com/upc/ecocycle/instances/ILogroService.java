package com.upc.ecocycle.instances;

import com.upc.ecocycle.dto.LogroDTO;

import java.util.List;

public interface ILogroService {
    String registrarLogro(LogroDTO logroDTO);
    List<LogroDTO> listarLogrosPorVecino(Integer vecinoId);
}
