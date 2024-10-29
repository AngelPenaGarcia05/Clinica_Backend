package com.clinicavillegas.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinicavillegas.application.models.Cita;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    
}
