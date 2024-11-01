package com.clinicavillegas.application.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinicavillegas.application.models.TipoDocumento;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {
    Optional<TipoDocumento> findByNombre(String nombre);
}
