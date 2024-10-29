package com.clinicavillegas.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinicavillegas.application.models.TipoTratamiento;

public interface TipoTratamientoRepository extends JpaRepository<TipoTratamiento, Long> {
    
}
