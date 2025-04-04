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
    LocalDate fecha;
    LocalTime hora;
    BigDecimal monto;

    String nombres;
    String apellidoPaterno;
    String apellidoMaterno;
    String tipoDocumento;
    String numeroIdentidad;
    String sexo;
    LocalDate fechaNacimiento;

    Long dentistaId;
    Long usuarioId;
    Long tratamientoId;
}
