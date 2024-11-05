package com.clinicavillegas.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.repositories.DentistaRepository;
import com.clinicavillegas.application.specifications.DentistaSpecification;
import com.clinicavillegas.application.models.Dentista;

@Service
public class DentistaService {
    @Autowired
    private DentistaRepository dentistaRepository;

    public List<Dentista> obtenerDentistas() {
        return dentistaRepository.findAll();
    }
    public List<Dentista> obtenerDentistas(String nombre, String especializacion) {
        Specification<Dentista> specs = DentistaSpecification.conNombre(nombre)
            .and(DentistaSpecification.conEspecializacion(especializacion));
        return dentistaRepository.findAll(specs);
    }
}
