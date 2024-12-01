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

import com.clinicavillegas.application.dto.DentistaRequest;
import com.clinicavillegas.application.dto.DentistaResponse;
import com.clinicavillegas.application.services.DentistaService;

@RestController
@RequestMapping("/api/dentistas")
public class DentistaController {
    @Autowired
    private DentistaService dentistaService;

    @GetMapping
    public ResponseEntity<List<DentistaResponse>> obtenerDentistas(
        @RequestParam(name = "nombre", required = false) String nombre,
        @RequestParam(name = "especializacion", required = false) String especializacion,
        @RequestParam(name = "usuarioId", required = false) Long usuarioId
    ) {
        List<DentistaResponse> dentistas = dentistaService.obtenerDentistas(nombre, especializacion, usuarioId);
        return ResponseEntity.ok(dentistas);
    }

    @GetMapping("/especialidades")
    public ResponseEntity<List<String>> obtenerEspecialidades(){
        return ResponseEntity.ok(dentistaService.obtenerEspecialidades());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> agregarDentista(@RequestBody DentistaRequest request){
        dentistaService.agregarDentista(request);
        return ResponseEntity.ok(Map.of("mensaje", "Dentista agregado con exito"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarDentista(@PathVariable("id") Long id, @RequestBody DentistaRequest request){
        dentistaService.actualizarDentista(id, request);
        return ResponseEntity.ok(Map.of("mensaje", "Dentista actualizado con exito"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarDentista(@PathVariable("id") Long id){
        dentistaService.eliminarDentista(id);
        return ResponseEntity.ok(Map.of("mensaje", "Dentista eliminado con exito"));
    }
}
