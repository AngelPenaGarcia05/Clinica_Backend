package com.clinicavillegas.application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinicavillegas.application.models.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByComentario(Comentario comentario);
}
