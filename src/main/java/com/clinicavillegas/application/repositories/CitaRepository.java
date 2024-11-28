package com.clinicavillegas.application.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.clinicavillegas.application.models.Cita;
import com.clinicavillegas.application.models.Dentista;
import com.clinicavillegas.application.models.Usuario;

public interface CitaRepository extends JpaRepository<Cita, Long>, JpaSpecificationExecutor<Cita> {
    
    List<Cita> findByUsuario(Usuario usuario);

    List<Cita> findByDentista(Dentista dentista);

    List<Cita> findByFecha(LocalDate fecha);

    @Query(value = "SELECT MONTH(fecha) AS mes, sexo, COUNT(*) AS total " +
                "FROM cita " +
                "WHERE YEAR(fecha) = :year " +
                "AND MONTH(fecha) = :month " +
                "GROUP BY MONTH(fecha), sexo " +
                "ORDER BY MONTH(fecha), sexo", 
       nativeQuery = true)
    List<Object[]> countCitasByMonthAndSexo(@Param("year") int year, @Param("month") int month);

    @Query(value = "SELECT MONTH(c.fecha) AS mes, tt.nombre AS tipo_tratamiento, COUNT(*) AS total " +
            "FROM cita c " +
            "JOIN tratamiento t ON c.tratamiento_id = t.id " +
            "JOIN tipo_tratamiento tt ON t.tipo_tratamiento_id = tt.id " +
            "WHERE YEAR(c.fecha) = :year " +
            "AND MONTH(c.fecha) = :month " +
            "GROUP BY MONTH(c.fecha), tt.nombre " +
            "ORDER BY mes, tipo_tratamiento;", nativeQuery = true)
    List<Object[]> countCitasByMonthAndTipoTratamiento(@Param("year") int year, @Param("month") int month);

    @Query(
        value = "SELECT MONTH(fecha) AS mes, COUNT(*) AS total " +
                "FROM cita " +
                "WHERE YEAR(fecha) = :year " +
                "AND estado = :estado " +
                "GROUP BY MONTH(fecha) " +
                "ORDER BY MONTH(fecha);",
        nativeQuery = true
    )
    List<Object[]> countCitasByMonthAndEstado(@Param("year") int year, @Param("estado") String estado);
}
