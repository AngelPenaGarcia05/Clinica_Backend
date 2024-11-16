package com.clinicavillegas.application.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinicavillegas.application.dto.ComentarioRequest;
import com.clinicavillegas.application.dto.ComentarioResponse;
import com.clinicavillegas.application.services.ComentarioService;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {
    @Autowired
    private ComentarioService comentarioService;

    @GetMapping
    public ResponseEntity<List<ComentarioResponse>> obtenerComentarios(){
        return ResponseEntity.ok(comentarioService.obtenerComentarios());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> agregarComentario(@RequestBody ComentarioRequest request){
        comentarioService.agregarComentario(request);
        return ResponseEntity.ok(Map.of("mensaje", "Comentario agregado con exito"));
    }
}
