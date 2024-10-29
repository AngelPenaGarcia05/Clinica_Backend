package com.clinicavillegas.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinicavillegas.application.models.Horario;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    
}
