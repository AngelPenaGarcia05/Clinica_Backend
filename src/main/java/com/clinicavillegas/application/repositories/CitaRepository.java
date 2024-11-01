package com.clinicavillegas.application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinicavillegas.application.models.Cita;
import com.clinicavillegas.application.models.Dentista;
import com.clinicavillegas.application.models.Usuario;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    
    List<Cita> findByUsuario(Usuario usuario);

    List<Cita> findByDentista(Dentista dentista);
}
