package com.clinicavillegas.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinicavillegas.application.dto.CitaRequest;
import com.clinicavillegas.application.models.Cita;
import com.clinicavillegas.application.services.CitaService;

@RestController
@RequestMapping("/api/citas")
public class CitaController {
    
    @Autowired
    private CitaService citaService;

    @GetMapping
    public ResponseEntity<List<Cita>> obtenerCitas(){
        return ResponseEntity.ok(citaService.obtenerCitas());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Cita>> obtenerCitasPorUsuario(@PathVariable Long usuarioId){
        return ResponseEntity.ok(citaService.obtenerCitasPorUsuario(usuarioId));
    }

    @PostMapping
    public void agregarCita(@RequestBody CitaRequest citaRequest){
        citaService.agregarCita(citaRequest);
    }
}
