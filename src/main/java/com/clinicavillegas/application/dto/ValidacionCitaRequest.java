package com.clinicavillegas.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidacionCitaRequest {
    String fecha;
    String hora;
    Long tratamientoId;
    Long dentistaId;
}
