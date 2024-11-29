package com.clinicavillegas.application.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CitaCanceladaDTO {
    LocalDate fecha;
    Long total;
}
