package com.clinicavillegas.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.dto.ReporteResponse;
import com.clinicavillegas.application.dto.UsuarioResponse;
import com.clinicavillegas.application.models.Reporte;
import com.clinicavillegas.application.models.TipoReporte;
import com.clinicavillegas.application.repositories.ReporteRepository;
import com.clinicavillegas.application.repositories.TipoReporteRepository;
import com.clinicavillegas.application.repositories.UsuarioRepository;

@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private TipoReporteRepository tipoReporteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<ReporteResponse> obtenerReportes(){
        List<Reporte> reportes = reporteRepository.findAll();
        return reportes.stream().map(reporte -> ReporteResponse.builder()
                .id(reporte.getId())
                .usuario(UsuarioResponse.builder()
                        .id(reporte.getUsuario().getId())
                        .apellidoPaterno(reporte.getUsuario().getApellidoPaterno())
                        .apellidoMaterno(reporte.getUsuario().getApellidoMaterno())
                        .nombres(reporte.getUsuario().getNombres())
                        .telefono(reporte.getUsuario().getTelefono())
                        .correo(reporte.getUsuario().getCorreo())
                        .imagenPerfil(reporte.getUsuario().getImagenPerfil())
                        .fechaNacimiento(reporte.getUsuario().getFechaNacimiento())
                        .tipoDocumento(reporte.getUsuario().getTipoDocumento())
                        .numeroIdentidad(reporte.getUsuario().getNumeroIdentidad())
                        .sexo(reporte.getUsuario().getSexo())
                        .estado(reporte.getUsuario().isEstado())
                        .rol(reporte.getUsuario().getRol())
                        .fechaCreacion(reporte.getUsuario().getFechaCreacion())
                        .fechaModificacion(reporte.getUsuario().getFechaModificacion())
                        .build())
                .tipoReporte(reporte.getTipoReporte())
                .fechaCreacion(reporte.getFechaCreacion())
                .fechaModificacion(reporte.getFechaModificacion())
                .build()).toList();
    }

    public void agregarReporte(Long tipoReporteId, Long usuarioId) {
        TipoReporte tipoReporte = tipoReporteRepository.findById(tipoReporteId).orElseThrow();
        Reporte reporte = Reporte.builder()
                .tipoReporte(tipoReporte)
                .usuario(usuarioRepository.findById(usuarioId).orElseThrow())
                .build();
        reporteRepository.save(reporte);
    }

}
