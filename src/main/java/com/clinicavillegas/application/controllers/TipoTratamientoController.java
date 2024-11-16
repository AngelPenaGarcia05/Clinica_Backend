package com.clinicavillegas.application.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinicavillegas.application.dto.TipoTratamientoRequest;
import com.clinicavillegas.application.models.TipoTratamiento;
import com.clinicavillegas.application.services.TipoTratamientoService;

@RestController
@RequestMapping("/api/tipo-tratamiento")
public class TipoTratamientoController {
    @Autowired
    private TipoTratamientoService tipoTratamientoService;

    @GetMapping
    public ResponseEntity<List<TipoTratamiento>> obtenerTiposTratamiento() {
        return ResponseEntity.ok(tipoTratamientoService.obtenerTiposTratamiento());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> agregarTipoTratamiento(@RequestBody TipoTratamientoRequest request) {
        tipoTratamientoService.agregarTipoTratamiento(request);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Tipo de tratamiento agregado con exito");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarTipoTratamiento(@PathVariable("id") Long id, @RequestBody TipoTratamientoRequest request) {
        tipoTratamientoService.actualizarTipoTratamiento(id, request);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Tipo de tratamiento actualizado con exito");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarTipoTratamiento(@PathVariable("id") Long id) {
        tipoTratamientoService.eliminarTipoTratamiento(id);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Tipo de tratamiento eliminado con exito");
        return ResponseEntity.ok(response);
    }
}
