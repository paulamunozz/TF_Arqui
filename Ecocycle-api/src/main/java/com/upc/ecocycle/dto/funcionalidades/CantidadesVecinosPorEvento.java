package com.upc.ecocycle.dto.funcionalidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CantidadesVecinosPorEvento {
    private Long mujeres;
    private Long hombres;

    private Long _15_24_;
    private Long _25_34_;
    private Long _35_44_;
    private Long _45_54_;
    private Long _55_64_;
    private Long _65_mas_;
}
