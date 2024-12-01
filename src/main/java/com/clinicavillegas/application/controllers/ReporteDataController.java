package com.clinicavillegas.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinicavillegas.application.dto.ReporteResponse;
import com.clinicavillegas.application.services.ReporteService;

@RestController
@RequestMapping("/api/data-reportes")
public class ReporteDataController {
    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public ResponseEntity<List<ReporteResponse>> obtenerReportes() {
        return ResponseEntity.ok(reporteService.obtenerReportes());
    }

}
