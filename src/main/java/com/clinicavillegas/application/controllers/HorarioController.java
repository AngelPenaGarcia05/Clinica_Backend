package com.clinicavillegas.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinicavillegas.application.models.Horario;
import com.clinicavillegas.application.services.HorarioService;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {
    @Autowired
    private HorarioService horarioService;

    @GetMapping
    public ResponseEntity<List<Horario>> obtenerHorarios(
        @RequestParam(name = "dentistaId", required = false) Long dentistaId
    ){
        return ResponseEntity.ok(horarioService.obtenerHorarios(dentistaId));
    }
}
