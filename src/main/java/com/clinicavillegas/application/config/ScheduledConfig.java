package com.clinicavillegas.application.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.clinicavillegas.application.models.Cita;
import com.clinicavillegas.application.repositories.CitaRepository;
import com.clinicavillegas.application.services.EmailService;
import com.clinicavillegas.application.specifications.CitaSpecifications;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
public class ScheduledConfig {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 0 * * *")
    public void executeTask() {
        Specification<Cita> specs = CitaSpecifications.conFecha(LocalDateTime.now().toLocalDate().plusDays(1))
                                    .and(CitaSpecifications.conEstado("Pendiente"));
        List<Cita> citas = citaRepository.findAll(specs);
        for (Cita cita : citas) {
            emailService.enviarRecordatorio(cita);
        }
    }
}

