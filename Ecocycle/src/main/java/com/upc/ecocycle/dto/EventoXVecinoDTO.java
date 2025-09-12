package com.upc.ecocycle.dto;

import com.upc.ecocycle.validations.Create;
import com.upc.ecocycle.validations.Update;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class EventoXVecinoDTO {
    @NotNull(groups = Update.class, message="Tiene que ingresar el idEXV")
    private Integer idEXV;

    @NotNull(groups = Create.class, message="Tiene que ingresar el eventoId")
    private Integer eventoId;

    @NotNull(groups = Create.class, message="Tiene que ingresar el vecinoId")
    private Integer vecinoId;

    @Size(message = "El comentario no puede ser mayor a 500 caracteres", max = 500)
    private String comentario;
}
