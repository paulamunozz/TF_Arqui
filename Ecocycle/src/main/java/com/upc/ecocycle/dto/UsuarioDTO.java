package com.upc.ecocycle.dto;

import com.upc.ecocycle.validations.Create;
import com.upc.ecocycle.validations.Update;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UsuarioDTO {

    @NotNull(groups = Update.class, message="Tiene que ingresar su idUsuario")
    private Integer idUsuario;

    @NotBlank(groups = Create.class, message="Tiene que ingresar su código")
    @Pattern(groups = {Create.class, Update.class}, regexp = "(^$|^.{8}$)", message = "El formato del código ingresado es incorrecto")
    private String codigo;

    @NotBlank(groups = Create.class, message="Tiene que ingresar su contraseña")
    private String contrasena;
}
