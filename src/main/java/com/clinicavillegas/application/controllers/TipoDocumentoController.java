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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinicavillegas.application.dto.TipoDocumentoRequest;
import com.clinicavillegas.application.models.TipoDocumento;
import com.clinicavillegas.application.services.TipoDocumentoService;

@RestController
@RequestMapping("/api/tipo-documento")
public class TipoDocumentoController {
    @Autowired
    private TipoDocumentoService tipoDocumentoService;

    @GetMapping
    public ResponseEntity<List<TipoDocumento>> obtenerTiposDocumento(
        @RequestParam(name = "nombre", required = false) String nombre,
        @RequestParam(name = "acronimo", required = false) String acronimo
    ){
        List<TipoDocumento> tiposDocumento = tipoDocumentoService.obtenerTiposDocumento(nombre, acronimo);
        if (tiposDocumento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tiposDocumento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoDocumento> obtenerTipoDocumento(@PathVariable("id") Long id){
        TipoDocumento tipoDocumento = tipoDocumentoService.obtenerTipoDocumento(id);
        if (tipoDocumento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipoDocumento);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> agregarTipoDocumento(@RequestBody TipoDocumentoRequest tipoDocumentoRequest){
        tipoDocumentoService.agregarTipoDocumento(tipoDocumentoRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Tipo de documento agregado con exito");
        return ResponseEntity.ok(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarTipoDocumento(@PathVariable("id") Long id, @RequestBody TipoDocumentoRequest tipoDocumentoRequest){
        tipoDocumentoService.actualizarTipoDocumento(id, tipoDocumentoRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Tipo de documento actualizado con exito");
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarTipoDocumento(@PathVariable("id") Long id){
        tipoDocumentoService.eliminarTipoDocumento(id);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Tipo de documento eliminado con exito");
        return ResponseEntity.ok(response);
    }
}
