package com.clinicavillegas.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CitaRequest {
    private LocalDate fecha;
    private LocalTime hora;
    private BigDecimal monto;
    private Long dentistaId;
    private Long usuarioId;
    private Long tratamientoId;
}
