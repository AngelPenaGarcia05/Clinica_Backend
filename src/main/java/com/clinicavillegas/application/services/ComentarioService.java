package com.clinicavillegas.application.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.dto.ComentarioRequest;
import com.clinicavillegas.application.dto.ComentarioResponse;
import com.clinicavillegas.application.models.Comentario;
import com.clinicavillegas.application.repositories.ComentarioRepository;
import com.clinicavillegas.application.repositories.UsuarioRepository;

@Service
public class ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<ComentarioResponse> obtenerComentarios() {
        List<Comentario> comentarios = comentarioRepository.findByComentario(null);
        List<ComentarioResponse> comentariosResponse = new ArrayList<ComentarioResponse>();
        for (Comentario comentario : comentarios) {
            if (comentario.getComentario() == null) {
                comentariosResponse.add(ComentarioResponse.builder()
                        .id(comentario.getId())
                        .contenido(comentario.getContenido())
                        .fecha(comentario.getFecha())
                        .nombresUsuario(comentario.getUsuario().getNombres())
                        .emailUsuario(comentario.getUsuario().getCorreo())
                        .imagenUsuario(comentario.getUsuario().getImagenPerfil())
                        .comentarios(obtenerRespuestas(comentario.getId()))
                        .build());
            }
        }
        return comentariosResponse;

    }

    public void agregarComentario(ComentarioRequest request) {
        Comentario comentario = Comentario.builder()
                .contenido(request.getContenido())
                .usuario(usuarioRepository.findById(request.getUsuarioId()).orElseThrow())
                .build();
        if (request.getComentarioId() != null) {
            comentario.setComentario(comentarioRepository.findById(request.getComentarioId()).orElseThrow());
        }
        comentarioRepository.save(comentario);
    }

    public List<ComentarioResponse> obtenerRespuestas(Long comentarioId) {
        Comentario comentario = comentarioRepository.findById(comentarioId).orElseThrow();
        List<Comentario> comentarios = comentarioRepository.findByComentario(comentario);
        List<ComentarioResponse> comentariosResponse = new ArrayList<ComentarioResponse>();
        for (Comentario comentarioResponse : comentarios) {
            comentariosResponse.add(ComentarioResponse.builder()
                    .id(comentarioResponse.getId())
                    .contenido(comentarioResponse.getContenido())
                    .fecha(comentarioResponse.getFecha())
                    .nombresUsuario(comentarioResponse.getUsuario().getNombres())
                    .emailUsuario(comentarioResponse.getUsuario().getCorreo())
                    .imagenUsuario(comentarioResponse.getUsuario().getImagenPerfil())
                    .build());
        }
        return comentariosResponse;
    }
}
