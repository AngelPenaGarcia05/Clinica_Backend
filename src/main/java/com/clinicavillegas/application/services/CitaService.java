package com.clinicavillegas.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.clinicavillegas.application.models.Cita;
import com.clinicavillegas.application.models.Dentista;
import com.clinicavillegas.application.models.Usuario;
import com.clinicavillegas.application.repositories.CitaRepository;
import com.clinicavillegas.application.repositories.DentistaRepository;
import com.clinicavillegas.application.repositories.UsuarioRepository;

public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DentistaRepository dentistaRepository;

    public List<Cita> obtenerCitasPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
        return citaRepository.findByUsuario(usuario);
    }
    
    public List<Cita> obtenerCitasPorDentista(Long dentistaId){
        Dentista dentista = dentistaRepository.findById(dentistaId).orElseThrow();
        return citaRepository.findByDentista(dentista);
    }
}
