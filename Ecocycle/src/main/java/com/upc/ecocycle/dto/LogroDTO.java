package com.upc.ecocycle.dto;

import com.upc.ecocycle.validations.Create;
import com.upc.ecocycle.validations.Update;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LogroDTO {
    private Integer idLogro;

    @NotNull(groups = Create.class, message = "Ingrese el vecinoId")
    private Integer vecinoId;

    @NotBlank(groups = Create.class, message = "Ingrese el nombre")
    @Size(groups = {Create.class, Update.class}, max = 50, message="El nombre no puede superar los 50 caracteres")
    private String nombre;
}
