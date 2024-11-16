package com.clinicavillegas.application.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TratamientoRequest {
    String nombre;
    String descripcion;
    BigDecimal costo;
    String imagenURL;
    int duracion;
    Long tipoTratamientoId;
}
