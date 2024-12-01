package com.clinicavillegas.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import com.clinicavillegas.application.models.TipoDocumento;
import com.clinicavillegas.application.models.Tratamiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CitaResponse {
    Long id;
    LocalDate fecha;
    LocalTime hora;
    BigDecimal monto;

    String nombres;
    String apellidoPaterno;
    String apellidoMaterno;
    TipoDocumento tipoDocumento;
    String numeroIdentidad;
    String sexo;
    String telefono;
    LocalDate fechaNacimiento;
    String estado;
    DentistaResponse dentista;
    Long usuarioId;
    Tratamiento tratamiento;
}
