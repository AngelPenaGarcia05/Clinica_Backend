package com.clinicavillegas.application.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinicavillegas.application.dto.CodeRequest;
import com.clinicavillegas.application.dto.EmailRequest;
import com.clinicavillegas.application.services.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/sendemail")
    public String sendEmail(@RequestBody EmailRequest request){
        return emailService.sendEmail(request);
    }

    @PostMapping("/sendcode")
    public ResponseEntity<Map<String, Object>> sendCode(@RequestBody CodeRequest request){
        Map<String, Object> response = new java.util.HashMap<>();
        response.put("code", emailService.sendCode(request.getEmail()));
        return ResponseEntity.ok(response);
    }

}
