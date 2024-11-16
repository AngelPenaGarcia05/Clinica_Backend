package com.clinicavillegas.application.dto;

import java.time.LocalDateTime;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioResponse {
    Long id;
    String contenido;
    LocalDateTime fecha;
    String nombresUsuario;
    String emailUsuario;
    String imagenUsuario;
    List<ComentarioResponse> comentarios;
}
