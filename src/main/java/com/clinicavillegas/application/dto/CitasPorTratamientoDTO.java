package com.clinicavillegas.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitasPorTratamientoDTO {
    private int mes;
    private String tipoTratamiento;
    private long total;
}
