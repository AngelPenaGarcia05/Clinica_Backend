package com.clinicavillegas.application.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.clinicavillegas.application.models.Dentista;
import com.clinicavillegas.application.models.Usuario;

public interface DentistaRepository extends JpaRepository<Dentista, Long>, JpaSpecificationExecutor<Dentista> {
    Optional<Dentista> findByUsuario(Usuario usuario);

    @Query("SELECT DISTINCT d.especializacion FROM Dentista d")
    List<String> findEspecializaciones();
}
