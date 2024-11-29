package com.clinicavillegas.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CitaDentistaDTO {
    Long dentistaId;
    String dentistaColegiatura;
    String dentistaNombres;
    Long total;
}
