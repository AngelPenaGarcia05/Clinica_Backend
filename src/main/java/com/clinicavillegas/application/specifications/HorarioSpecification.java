package com.clinicavillegas.application.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.clinicavillegas.application.models.Dia;
import com.clinicavillegas.application.models.Horario;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class HorarioSpecification {
    public static Specification<Horario> conDia(String dia) {
        return (Root<Horario> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (dia == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("dia"), Dia.valueOf(dia));
        };
    }
    public static Specification<Horario> conDentistaId(Long dentistaId) {
        return (Root<Horario> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (dentistaId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("dentista").get("id"), dentistaId);
        };
    }
}
