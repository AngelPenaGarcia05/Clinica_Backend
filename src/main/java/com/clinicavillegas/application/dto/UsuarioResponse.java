package com.clinicavillegas.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.clinicavillegas.application.models.Rol;
import com.clinicavillegas.application.models.Sexo;
import com.clinicavillegas.application.models.TipoDocumento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse {
    private Long id;

    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;

    private String numeroIdentidad;
    private Sexo sexo;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String correo;
    private String imagenPerfil;
    private boolean estado;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    
    private Rol rol;
    private TipoDocumento tipoDocumento;
}
