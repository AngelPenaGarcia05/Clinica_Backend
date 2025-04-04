package com.clinicavillegas.application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.clinicavillegas.application.models.Dentista;
import com.clinicavillegas.application.models.Horario;

public interface HorarioRepository extends JpaRepository<Horario, Long>, JpaSpecificationExecutor<Horario>{
    List<Horario> findByDentista(Dentista dentista);
}
