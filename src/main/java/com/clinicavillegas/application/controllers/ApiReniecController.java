package com.clinicavillegas.application.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinicavillegas.application.services.ApiReniecService;

@RestController
@RequestMapping("/api/reniec")
public class ApiReniecController {
    @Autowired
    private ApiReniecService apiReniecService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> consultarDni(@RequestParam(name = "dni", required = true) String dni) {
        Map<String, Object> response = apiReniecService.consultarDni(dni);
        return ResponseEntity.ok(response);
    }
}
