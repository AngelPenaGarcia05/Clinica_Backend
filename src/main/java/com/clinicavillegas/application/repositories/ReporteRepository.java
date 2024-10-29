package com.clinicavillegas.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinicavillegas.application.models.Reporte;

public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    
}
