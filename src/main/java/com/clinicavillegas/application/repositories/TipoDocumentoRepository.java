package com.clinicavillegas.application.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.clinicavillegas.application.models.TipoDocumento;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Long>, JpaSpecificationExecutor<TipoDocumento> {
    Optional<TipoDocumento> findByNombre(String nombre);
    Optional<TipoDocumento> findByAcronimo(String acronimo);
}
