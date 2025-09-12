package com.upc.ecocycle.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.upc.ecocycle.enitites.Vecino;
import com.upc.ecocycle.validations.Create;
import com.upc.ecocycle.validations.Update;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ReciclajeDTO {
    @NotNull(groups = Update.class, message="Tiene que ingresar el idReciclaje")
    private Integer idReciclaje;

    @NotNull(groups = Create.class, message="Tiene que ingresar el vecinoId")
    private Integer vecinoId;

    @NotNull(groups = {Create.class, Update.class}, message="Tiene que ingresar el peso")
    @Digits(groups = {Create.class, Update.class}, integer = 5, fraction = 2, message="Formato incorrecto de peso")
    private BigDecimal peso;

    @NotNull(groups = {Create.class, Update.class}, message="Tiene que ingresar el tipo")
    @Size(groups = {Create.class, Update.class}, max = 50, message="El tipo no puede superar los 50 caracteres")
    private String tipo;

    @NotNull(groups = {Create.class, Update.class}, message="Tiene que ingresar el método")
    @Size(groups = {Create.class, Update.class}, max = 50, message="El método no puede superar los 50 caracteres")
    private String metodo;

    @NotNull(groups = {Create.class, Update.class}, message="Tiene que ingresar la fecha")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fecha;

    private Integer puntaje;
}
