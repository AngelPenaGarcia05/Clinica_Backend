package com.clinicavillegas.application.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.clinicavillegas.application.models.TipoTratamiento;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class TipoTratamientoSpecification {
    public static Specification<TipoTratamiento> conEstado(Boolean estado){
        return (Root<TipoTratamiento> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (estado == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("estado"), estado);
        };
    }
}
