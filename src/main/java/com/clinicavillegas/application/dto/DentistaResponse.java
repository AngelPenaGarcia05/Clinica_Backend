package com.clinicavillegas.application.dto;

import com.clinicavillegas.application.models.TipoDocumento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DentistaResponse {
    Long id;
    String nColegiatura;
    boolean estado;
    String especializacion;
    String nombres;
    String apellidoPaterno;
    String apellidoMaterno;
    TipoDocumento tipoDocumento;
    String numeroIdentidad;
    String sexo;
    String fechaNacimiento;
}
