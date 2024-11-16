package com.clinicavillegas.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.dto.HorarioRequest;
import com.clinicavillegas.application.dto.HorarioResponse;
import com.clinicavillegas.application.models.Dentista;
import com.clinicavillegas.application.models.Dia;
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

    public List<HorarioResponse> obtenerHorarios(Long dentistaId){
        Dentista dentista = dentistaRepository.findById(dentistaId).orElseThrow();
        List<Horario> horarios = horarioRepository.findByDentista(dentista);
        return horarios.stream().map(horario -> HorarioResponse.builder()
                .id(horario.getId())
                .dia(horario.getDia().toString())
                .horaComienzo(horario.getHoraComienzo())
                .horaFin(horario.getHoraFin())
                .dentistaId(horario.getDentista().getId())
                .build()).toList();
    }

    public void agregarHorario(HorarioRequest request) {
        Horario horario = Horario.builder()
                .dia(Dia.valueOf(request.getDia()))
                .horaComienzo(request.getHoraComienzo())
                .horaFin(request.getHoraFin())
                .dentista(dentistaRepository.findById(request.getDentistaId()).orElseThrow())
                .build();
        horarioRepository.save(horario);
    }

    public void eliminarHorario(Long id) {
        horarioRepository.deleteById(id);
    }
}
