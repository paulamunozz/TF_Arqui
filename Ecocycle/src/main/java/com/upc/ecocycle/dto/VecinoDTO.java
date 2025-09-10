package com.upc.ecocycle.dto;

import com.upc.ecocycle.validations.Create;
import com.upc.ecocycle.validations.Update;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class VecinoDTO {
    private Integer idVecino;

    @NotNull(groups = {Create.class, Update.class}, message="Ingrese el usuarioId")
    private Integer usuarioId;

    @NotBlank(groups = Create.class, message="Ingrese el nombre")
    private String nombre;

    @NotBlank(groups = Create.class, message = "Ingrese el genero")
    @Pattern(groups = Create.class, regexp = "^[FM]$", message = "El género solo puede ser F o M")
    private String genero;

    @NotNull(groups = Create.class, message = "Ingrese la edad")
    @Min(value = 0, groups = {Create.class, Update.class}, message = "La edad no puede ser menor que 0")
    @Max(value = 200, groups = {Create.class, Update.class}, message = "La edad no puede ser mayor que 200")
    private Integer edad;

    @NotBlank(groups = Create.class, message="Ingrese el distrito")
    private String distrito;

    @NotBlank(groups = Create.class, message="Ingrese la direccion")
    private String direccion;

    private Integer puntajetotal;
    private Integer icono;
    private Integer puesto;
}
