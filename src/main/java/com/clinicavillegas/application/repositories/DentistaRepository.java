package com.clinicavillegas.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinicavillegas.application.models.Dentista;

public interface DentistaRepository extends JpaRepository<Dentista, Long> {
    
}
