package com.clinicavillegas.application.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.clinicavillegas.application.models.Dentista;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class DentistaSpecification {
    public static Specification<Dentista> conNombre(String nombre) {
        return (Root<Dentista> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (nombre == null) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("usuario").get("nombres")), "%" + nombre.toLowerCase() + "%");
        };
    }

    public static Specification<Dentista> conEspecializacion(String especializacion) {
        return (Root<Dentista> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (especializacion == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("especializacion"), especializacion);
        };
    }

    public static Specification<Dentista> conEstado(Boolean estado){
        return (Root<Dentista> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (estado == null) {
                builder.conjunction();
            }
            return builder.equal(root.get("estado"), estado);
        };
    }
    public static Specification<Dentista> conUsuarioId(Long usuarioId){
        return (Root<Dentista> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (usuarioId == null) {
                builder.conjunction();
            }
            return builder.equal(root.get("usuario").get("id"), usuarioId);
        };
    }
}
