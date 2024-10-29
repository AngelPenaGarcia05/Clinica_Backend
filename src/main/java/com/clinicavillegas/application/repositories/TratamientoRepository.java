package com.clinicavillegas.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinicavillegas.application.models.Tratamiento;

public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
    
}
