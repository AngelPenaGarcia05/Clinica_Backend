package com.clinicavillegas.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinicavillegas.application.models.Dentista;
import com.clinicavillegas.application.services.DentistaService;

@RestController
@RequestMapping("/api/dentistas")
public class DentistaController {
    @Autowired
    private DentistaService dentistaService;

    @GetMapping
    public ResponseEntity<List<Dentista>> obtenerDentistas(
        @RequestParam(name = "nombre", required = false) String nombre,
        @RequestParam(name = "especializacion", required = false) String especializacion
    ) {
        List<Dentista> dentistas = dentistaService.obtenerDentistas(nombre, especializacion);
        return ResponseEntity.ok(dentistas);
    }
}
