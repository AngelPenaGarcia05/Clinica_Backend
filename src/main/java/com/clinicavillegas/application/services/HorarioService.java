package com.clinicavillegas.application.services;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.dto.HorarioRequest;
import com.clinicavillegas.application.dto.HorarioResponse;
import com.clinicavillegas.application.models.Dia;
import com.clinicavillegas.application.models.Horario;
import com.clinicavillegas.application.repositories.DentistaRepository;
import com.clinicavillegas.application.repositories.HorarioRepository;
import com.clinicavillegas.application.specifications.HorarioSpecification;

@Service
public class HorarioService {
    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    public DentistaRepository dentistaRepository;

    public List<Horario> obteneHorarios() {
        return horarioRepository.findAll();
    }

    public List<HorarioResponse> obtenerHorarios(Long dentistaId, String dia){
        Specification<Horario> specs = HorarioSpecification.conDentistaId(dentistaId).and(HorarioSpecification.conDia(dia));
        List<Horario> horarios = horarioRepository.findAll(specs);
        return horarios.stream().map(horario -> HorarioResponse.builder()
                .id(horario.getId())
                .dia(horario.getDia().toString())
                .horaComienzo(horario.getHoraComienzo())
                .horaFin(horario.getHoraFin())
                .dentistaId(horario.getDentista().getId())
                .build()).toList();
    }

    public void agregarHorario(HorarioRequest request) {
        //que haya un diferencia minima de 8 horas entre ellos
        if (Duration.between(request.getHoraComienzo(), request.getHoraFin()).abs().toHours() < 8) {
            throw new IllegalArgumentException("El tiempo de horario no puede ser menor a 8 horas");
            
        }
        if (request.getHoraComienzo().isAfter(request.getHoraFin())) {
            throw new IllegalArgumentException("La hora de finalización no puede ser anterior a la hora de inicio");
        }
        Specification<Horario> specs = HorarioSpecification.conDentistaId(request.getDentistaId());
        List<Horario> horarios = horarioRepository.findAll(specs);
        for (Horario horario : horarios) {
            if (horario.getDia() == Dia.valueOf(request.getDia())) {
                throw new IllegalArgumentException("Ya existe un horario para ese dia");
            }
        }
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
