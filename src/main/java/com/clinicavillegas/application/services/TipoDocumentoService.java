package com.clinicavillegas.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.dto.TipoDocumentoRequest;
import com.clinicavillegas.application.models.TipoDocumento;
import com.clinicavillegas.application.repositories.TipoDocumentoRepository;
import com.clinicavillegas.application.specifications.TipoDocumentoSpecification;

@Service
public class TipoDocumentoService {
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    public void agregarTipoDocumento(TipoDocumentoRequest request) {
        tipoDocumentoRepository.save(
                TipoDocumento.builder()
                        .nombre(request.getNombre())
                        .acronimo(request.getAcronimo())
                        .estado(true)
                        .build());
    }

    public List<TipoDocumento> obtenerTiposDocumento() {
        return tipoDocumentoRepository.findAll();
    }

    public List<TipoDocumento> obtenerTiposDocumento(String nombre, String acronimo) {
        Specification<TipoDocumento> specs = TipoDocumentoSpecification.conNombre(nombre)
                .and(TipoDocumentoSpecification.conAcronimo(acronimo).and(TipoDocumentoSpecification.conEstado(true)));
        return tipoDocumentoRepository.findAll(specs);
    }

    public void actualizarTipoDocumento(Long id, TipoDocumentoRequest request) {
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(id).get();
        tipoDocumento.setNombre(request.getNombre());
        tipoDocumento.setAcronimo(request.getAcronimo());
        tipoDocumentoRepository.save(tipoDocumento);
    }

    public void eliminarTipoDocumento(Long id) {
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(id).get();
        tipoDocumento.setEstado(false);
        tipoDocumentoRepository.save(tipoDocumento);
    }

    public TipoDocumento obtenerTipoDocumento(Long id) {
        return tipoDocumentoRepository.findById(id).get();
    }
}
