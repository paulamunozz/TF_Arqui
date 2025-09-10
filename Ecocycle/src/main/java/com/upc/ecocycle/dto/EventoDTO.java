package com.upc.ecocycle.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.upc.ecocycle.validations.Create;
import com.upc.ecocycle.validations.Update;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class EventoDTO {
    @NotNull(groups = Update.class, message="Tiene que ingresar el idEvento")
    private Integer idEvento;

    @NotNull(groups = Create.class, message="Tiene que ingresar la municipalidadId")
    private Integer municipalidadId;

    @NotBlank(groups = Create.class, message="Ingrese el nombre")
    @Size(groups = {Create.class, Update.class}, max = 50, message="El nombre no puede superar los 50 caracteres")
    private String nombre;

    @NotBlank(groups = Create.class, message="Ingrese la descripción")
    @Size(groups = {Create.class, Update.class}, max = 300, message="La descripción no puede superar los 300 caracteres")
    private String descripcion;

    @NotNull(groups = Create.class, message="Tiene que ingresar el peso objetivo")
    @Digits(integer = 5, fraction = 2, message="Formato incorrecto de peso objetivo")
    private BigDecimal pesoobjetivo;

    @NotNull(groups = Create.class, message="Tiene que ingresar la fecha de inicio")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechainicio;

    @NotNull(groups = Create.class, message="Tiene que ingresar la fecha de fin")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechafin;
}
