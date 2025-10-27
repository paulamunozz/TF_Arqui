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

    @NotBlank(groups = Create.class, message="Tiene que ingresar su DNI")
    @Pattern(groups = {Create.class, Update.class}, regexp = "(^$|^.{8}$)", message = "El formato del DNI ingresado es incorrecto")
    private String dni;

    @NotBlank(groups = Create.class, message="Tiene que ingresar su contraseña")
    private String contrasena;

    @NotBlank(groups = Create.class, message="Ingrese el nombre")
    private String nombre;

    @NotBlank(groups = Create.class, message = "Ingrese el genero")
    @Pattern(groups = Create.class, regexp = "^[FM]$", message = "El género solo puede ser F o M")
    private String genero;

    @NotNull(groups = Create.class, message = "Ingrese la edad")
    @Min(value = 15, groups = {Create.class, Update.class}, message = "La edad no puede ser menor que 15")
    @Max(value = 200, groups = {Create.class, Update.class}, message = "La edad no puede ser mayor que 200")
    private Integer edad;

    @NotBlank(groups = Create.class, message="Ingrese el distrito")
    private String distrito;

    private Integer puntajetotal;
    private Integer icono;
    private Integer puesto;
    private Boolean eliminado;
}
