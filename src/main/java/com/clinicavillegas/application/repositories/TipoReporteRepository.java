package com.clinicavillegas.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinicavillegas.application.models.TipoReporte;

public interface TipoReporteRepository extends JpaRepository<TipoReporte, Long> {
    
}
