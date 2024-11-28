package com.clinicavillegas.application.dto;

import com.clinicavillegas.application.models.Sexo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitasPorMesDTO {

    private int mes;
    private Sexo sexo;
    private long total;
}



