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

import com.clinicavillegas.application.dto.CitaRequest;
import com.clinicavillegas.application.dto.CitaResponse;
import com.clinicavillegas.application.dto.ValidacionCitaRequest;
import com.clinicavillegas.application.services.CitaService;

@RestController
@RequestMapping("/api/citas")
public class CitaController {
    
    @Autowired
    private CitaService citaService;

    @GetMapping
    public ResponseEntity<List<CitaResponse>> obtenerCitas(
        @RequestParam(name = "usuarioId", required = false) Long usuarioId,
        @RequestParam(name = "dentistaId", required = false) Long dentistaId,
        @RequestParam(name = "estado", required = false) String estado
    ){
        return ResponseEntity.ok(citaService.obtenerCitas(usuarioId, dentistaId, estado));
    }

    @PostMapping("/validar")
    public ResponseEntity<Boolean> validarCita(@RequestBody ValidacionCitaRequest request){
        boolean validacion = citaService.validarDisponibilidad(request);
        return ResponseEntity.ok(validacion);
    }

    @PutMapping("/atender/{id}")
    public ResponseEntity<Map<String, Object>> atenderCita(@PathVariable("id") Long id){
        citaService.atenderCita(id);
        return ResponseEntity.ok(Map.of("mensaje", "Cita atendida con exito"));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> agregarCita(@RequestBody CitaRequest citaRequest){
        citaService.agregarCita(citaRequest);
        return ResponseEntity.ok(Map.of("mensaje", "Cita agregada con exito"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarCita(@PathVariable("id") Long id, @RequestBody CitaRequest citaRequest){
        citaService.actualizarCita(id, citaRequest);
        return ResponseEntity.ok(Map.of("mensaje", "Cita actualizada con exito"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarCita(@PathVariable("id") Long id){
        citaService.eliminarCita(id);
        return ResponseEntity.ok(Map.of("mensaje", "Cita cancelada con exito"));
    }
}
