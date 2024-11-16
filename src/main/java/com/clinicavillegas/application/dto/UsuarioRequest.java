package com.clinicavillegas.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {
    String nombres;
    String apellidoPaterno;
    String apellidoMaterno;
    String telefono;
    String imagenPerfil;
}
