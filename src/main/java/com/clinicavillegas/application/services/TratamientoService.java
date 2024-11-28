package com.clinicavillegas.application.services;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.dto.TratamientoRequest;
import com.clinicavillegas.application.models.Tratamiento;
import com.clinicavillegas.application.repositories.TipoTratamientoRepository;
import com.clinicavillegas.application.repositories.TratamientoRepository;
import com.clinicavillegas.application.specifications.TratamientoSpecification;

@Service
public class TratamientoService {
    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Autowired
    private TipoTratamientoRepository tipoTratamientoRepository;

    public List<Tratamiento> obtenerTratamientos() {
        return tratamientoRepository.findAll();
    }

    public List<Tratamiento> obtenerTratamientos(Long tipoId, String nombre) {
        Specification<Tratamiento> specs = TratamientoSpecification.tipoTratamientoEquals(tipoId)
                .and(TratamientoSpecification.nombreEquals(nombre))
                .and(TratamientoSpecification.estadoEquals(true));
        return tratamientoRepository.findAll(specs);

    }

    public Tratamiento obtenerTratamiento(Long id) {
        return tratamientoRepository.findById(id).orElse(null);
    }

    public void guardarTratamiento(TratamientoRequest request) {
        Tratamiento tratamiento = Tratamiento.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .costo(request.getCosto())
                .duracion(Duration.ofMinutes(request.getDuracion()))
                .imagenURL(request.getImagenURL())
                .estado(true)
                .tipoTratamiento(tipoTratamientoRepository.findById(request.getTipoTratamientoId()).orElse(null))
                .build();
        tratamientoRepository.save(tratamiento);
    }

    public void actualizarTratamiento(Long id, TratamientoRequest request) {
        Tratamiento tratamiento = tratamientoRepository.findById(id).orElse(null);
        if (tratamiento != null) {
            tratamiento.setNombre(request.getNombre());
            tratamiento.setDescripcion(request.getDescripcion());
            tratamiento.setCosto(request.getCosto());
            tratamiento.setDuracion(Duration.ofMinutes(request.getDuracion()));
            tratamiento.setImagenURL(request.getImagenURL());
            tratamiento.setEstado(true);
            tratamiento.setTipoTratamiento(
                    tipoTratamientoRepository.findById(request.getTipoTratamientoId()).orElse(null));
            tratamientoRepository.save(tratamiento);
        }
    }

    public void eliminarTratamiento(Long id) {
        Tratamiento tratamiento = tratamientoRepository.findById(id).orElse(null);
        if (tratamiento != null) {
            tratamiento.setEstado(false);
        }
        tratamientoRepository.save(tratamiento);
    }

}
