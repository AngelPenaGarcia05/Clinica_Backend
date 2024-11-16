package com.clinicavillegas.application.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinicavillegas.application.dto.HorarioRequest;
import com.clinicavillegas.application.dto.HorarioResponse;
import com.clinicavillegas.application.services.HorarioService;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {
    @Autowired
    private HorarioService horarioService;

    @GetMapping
    public ResponseEntity<List<HorarioResponse>> obtenerHorarios(
        @RequestParam(name = "dentistaId", required = true) Long dentistaId,
        @RequestParam(name = "dia", required = false) String dia

    ){
        return ResponseEntity.ok(horarioService.obtenerHorarios(dentistaId));
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> agregarHorario(@RequestBody HorarioRequest request){
        horarioService.agregarHorario(request);
        return ResponseEntity.ok(Map.of("mensaje", "Horario agregado con exito"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarHorario(@PathVariable Long id){
        horarioService.eliminarHorario(id);
        return ResponseEntity.ok(Map.of("mensaje", "Horario eliminado con exito"));
    }
}
