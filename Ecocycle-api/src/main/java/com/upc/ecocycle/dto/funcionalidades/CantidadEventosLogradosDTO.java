package com.upc.ecocycle.dto.funcionalidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CantidadEventosLogradosDTO {
    private Long cantidadEventosLogrados;
    private Long cantidadEventosSinLograr;
}
