package com.clinicavillegas.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.dto.CitaRequest;
import com.clinicavillegas.application.models.Cita;
import com.clinicavillegas.application.models.Dentista;
import com.clinicavillegas.application.models.Tratamiento;
import com.clinicavillegas.application.models.Usuario;
import com.clinicavillegas.application.repositories.CitaRepository;
import com.clinicavillegas.application.repositories.DentistaRepository;
import com.clinicavillegas.application.repositories.TratamientoRepository;
import com.clinicavillegas.application.repositories.UsuarioRepository;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DentistaRepository dentistaRepository;

    @Autowired
    private TratamientoRepository tratamientoRepository;

    public List<Cita> obtenerCitas(){
        return citaRepository.findAll();
    }

    public List<Cita> obtenerCitasPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
        return citaRepository.findByUsuario(usuario);
    }
    
    public List<Cita> obtenerCitasPorDentista(Long dentistaId){
        Dentista dentista = dentistaRepository.findById(dentistaId).orElseThrow();
        return citaRepository.findByDentista(dentista);
    }

    public void agregarCita(CitaRequest citaRequest){
        Cita cita = Cita.builder()
            .fecha(citaRequest.getFecha())
            .hora(citaRequest.getHora())
            .monto(citaRequest.getMonto())
            .dentista(dentistaRepository.findById(citaRequest.getDentistaId()).orElseThrow())
            .usuario(usuarioRepository.findById(citaRequest.getUsuarioId()).orElseThrow())
            .tratamiento(tratamientoRepository.findById(citaRequest.getTratamientoId()).orElseThrow())
            .build();
        citaRepository.save(cita);
    }
}
