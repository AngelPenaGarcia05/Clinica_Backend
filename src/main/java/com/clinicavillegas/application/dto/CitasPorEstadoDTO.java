package com.clinicavillegas.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitasPorEstadoDTO {
    int mes;
    long total;
}
