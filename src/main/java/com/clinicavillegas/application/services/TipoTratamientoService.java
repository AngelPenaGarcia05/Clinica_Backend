package com.clinicavillegas.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.dto.TipoTratamientoRequest;
import com.clinicavillegas.application.models.TipoTratamiento;
import com.clinicavillegas.application.repositories.TipoTratamientoRepository;
import com.clinicavillegas.application.specifications.TipoTratamientoSpecification;

@Service
public class TipoTratamientoService {
    @Autowired
    private TipoTratamientoRepository tipoTratamientoRepository;

    public List<TipoTratamiento> obtenerTiposTratamiento() {
        Specification<TipoTratamiento> specs = TipoTratamientoSpecification.conEstado(true);
        return tipoTratamientoRepository.findAll(specs);
    }

    public void agregarTipoTratamiento(TipoTratamientoRequest request) {
        TipoTratamiento tipoTratamiento = TipoTratamiento.builder()
                .nombre(request.getNombre())
                .estado(true)
                .build();
        tipoTratamientoRepository.save(tipoTratamiento);
    }

    public void actualizarTipoTratamiento(Long id, TipoTratamientoRequest request) {
        TipoTratamiento tipoTratamiento = tipoTratamientoRepository.findById(id).get();
        tipoTratamiento.setNombre(request.getNombre());
        tipoTratamientoRepository.save(tipoTratamiento);
    }

    public void eliminarTipoTratamiento(Long id) {
        TipoTratamiento tipoTratamiento = tipoTratamientoRepository.findById(id).get();
        tipoTratamiento.setEstado(false);
        tipoTratamientoRepository.save(tipoTratamiento);
    }
}
