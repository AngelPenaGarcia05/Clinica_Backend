package com.clinicavillegas.application.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinicavillegas.application.dto.UsuarioRequest;
import com.clinicavillegas.application.dto.UsuarioResponse;
import com.clinicavillegas.application.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> obtenerClientes(
        @RequestParam(name = "nombre", required = false) String nombre,
        @RequestParam(name = "rol", required = false) String rol
    ) {
        return ResponseEntity.ok(usuarioService.obtenerClientes(nombre, rol));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerClientePorId(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.obtenerClientePorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarCliente(@PathVariable Long id, UsuarioRequest request){
        usuarioService.actualizarClientePorId(id, request);
        return ResponseEntity.ok(Map.of("mensaje", "Cliente actualizado con exito"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarCliente(@PathVariable Long id){
        usuarioService.eliminarCliente(id);
        return ResponseEntity.ok(Map.of("mensaje", "Cliente eliminado con exito"));
    }

}
