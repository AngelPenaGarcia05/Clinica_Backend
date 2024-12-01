package com.clinicavillegas.application.dto;

import java.time.LocalDateTime;

import com.clinicavillegas.application.models.TipoReporte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReporteResponse {
    Long id;
    UsuarioResponse usuario;
    TipoReporte tipoReporte;
    LocalDateTime fechaCreacion;
    LocalDateTime fechaModificacion;
}
