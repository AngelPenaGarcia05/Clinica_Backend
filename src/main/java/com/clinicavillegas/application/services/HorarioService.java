package com.clinicavillegas.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.models.Dentista;
import com.clinicavillegas.application.models.Horario;
import com.clinicavillegas.application.repositories.DentistaRepository;
import com.clinicavillegas.application.repositories.HorarioRepository;

@Service
public class HorarioService {
    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    public DentistaRepository dentistaRepository;

    public List<Horario> obteneHorarios() {
        return horarioRepository.findAll();
    }

    public List<Horario> obtenerHorarios(Long dentistaId){
        Dentista dentista = dentistaRepository.findById(dentistaId).orElseThrow();
        return horarioRepository.findByDentista(dentista);
    }
}
