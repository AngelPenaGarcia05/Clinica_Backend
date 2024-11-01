package com.clinicavillegas.application.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiReniecService {
 
    private final RestTemplate restTemplate;

    @Autowired
    public ApiReniecService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Map<String, Object> consultarDni(String dni) {
        String url = "https://api.apis.net.pe/v2/reniec/dni?numero=" + dni;
        String token = "apis-token-11158.utNTgTM5z7fbgTKQU2s9EkQkmkkukNH9";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.set("Referer", "https://apis.net.pe/consulta-dni-api");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, new ParameterizedTypeReference<Map<String, Object>>() {
                });

        return response.getBody();
    }
}
