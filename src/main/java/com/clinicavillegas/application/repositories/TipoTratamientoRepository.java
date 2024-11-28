package com.clinicavillegas.application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.clinicavillegas.application.models.TipoTratamiento;

public interface TipoTratamientoRepository extends JpaRepository<TipoTratamiento, Long>, JpaSpecificationExecutor<TipoTratamiento> {
    List<TipoTratamiento> findByEstado(boolean estado);
}
