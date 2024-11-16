package com.clinicavillegas.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.dto.TipoTratamientoRequest;
import com.clinicavillegas.application.models.TipoTratamiento;
import com.clinicavillegas.application.repositories.TipoTratamientoRepository;

@Service
public class TipoTratamientoService {
    @Autowired
    private TipoTratamientoRepository tipoTratamientoRepository;

    public List<TipoTratamiento> obtenerTiposTratamiento() {
        return tipoTratamientoRepository.findAll();
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
