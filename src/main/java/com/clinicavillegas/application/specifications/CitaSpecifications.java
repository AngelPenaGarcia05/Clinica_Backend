package com.clinicavillegas.application.specifications;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.clinicavillegas.application.models.Cita;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class CitaSpecifications {
    public static Specification<Cita> conUsuarioId(Long usuarioId) {
        return (Root<Cita> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (usuarioId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("usuario").get("id"), usuarioId);
        };
    }
    public static Specification<Cita> conDentistaId(Long dentistaId) {
        return (Root<Cita> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (dentistaId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("dentista").get("id"), dentistaId);
        };
    }
    public static Specification<Cita> conEstado(String estado) {
        return (Root<Cita> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (estado == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("estado"), estado);
        };
    }
    public static Specification<Cita> conFecha(LocalDate fecha) {
        return (Root<Cita> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (fecha == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("fecha"), fecha);
        };
    }
}
