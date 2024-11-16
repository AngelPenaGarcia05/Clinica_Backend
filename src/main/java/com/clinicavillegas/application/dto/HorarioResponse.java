package com.clinicavillegas.application.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HorarioResponse {
    Long id;
    String dia;
    LocalTime horaComienzo;
    LocalTime horaFin;
    Long dentistaId;
}
