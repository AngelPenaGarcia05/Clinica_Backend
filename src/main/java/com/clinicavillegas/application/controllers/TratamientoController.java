package com.clinicavillegas.application.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinicavillegas.application.dto.TratamientoRequest;
import com.clinicavillegas.application.models.Tratamiento;
import com.clinicavillegas.application.services.TratamientoService;

@RestController
@RequestMapping("/api/tratamientos")
public class TratamientoController {
    @Autowired
    private TratamientoService tratamientoService;

    @GetMapping
    public ResponseEntity<List<Tratamiento>> getTratamientos(
        @RequestParam(name = "tipo", required = false) Long tipoId
    ) {
        return ResponseEntity.ok(tratamientoService.obtenerTratamientos(tipoId));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> guardarTratamiento(@RequestBody TratamientoRequest request) {
        tratamientoService.guardarTratamiento(request);
        return ResponseEntity.ok(Map.of("mensaje", "Tratamiento guardado con exito"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarTratamiento(@PathVariable Long id, @RequestBody TratamientoRequest request) {
        tratamientoService.actualizarTratamiento(id, request);
        return ResponseEntity.ok(Map.of("mensaje", "Tratamiento actualizado con exito"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarTratamiento(@PathVariable Long id) {
        tratamientoService.eliminarTratamiento(id);
        return ResponseEntity.ok(Map.of("mensaje", "Tratamiento eliminado con exito"));
    }
    
}
