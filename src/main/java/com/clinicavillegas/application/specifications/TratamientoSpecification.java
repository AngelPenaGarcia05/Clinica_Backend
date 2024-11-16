package com.clinicavillegas.application.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.clinicavillegas.application.models.Tratamiento;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class TratamientoSpecification {
    public static Specification<Tratamiento> tipoTratamientoEquals(Long tipoId) {
        return (Root<Tratamiento> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (tipoId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("tipoTratamiento").get("id"), tipoId);
        };
    }
}
