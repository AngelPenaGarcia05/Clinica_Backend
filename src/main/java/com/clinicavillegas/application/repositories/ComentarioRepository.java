package com.clinicavillegas.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinicavillegas.application.models.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    
}
