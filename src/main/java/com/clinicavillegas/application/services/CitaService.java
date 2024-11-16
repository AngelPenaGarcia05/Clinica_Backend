package com.clinicavillegas.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.dto.CitaRequest;
import com.clinicavillegas.application.models.Cita;
import com.clinicavillegas.application.models.Dentista;
import com.clinicavillegas.application.models.Sexo;
import com.clinicavillegas.application.models.Usuario;
import com.clinicavillegas.application.repositories.CitaRepository;
import com.clinicavillegas.application.repositories.DentistaRepository;
import com.clinicavillegas.application.repositories.TipoDocumentoRepository;
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

    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

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
            .nombres(citaRequest.getNombres())
            .apellidoPaterno(citaRequest.getApellidoPaterno())
            .apellidoMaterno(citaRequest.getApellidoMaterno())
            .estado("Pendiente")
            .tipoDocumento(tipoDocumentoRepository.findByAcronimo(citaRequest.getTipoDocumento()).orElseThrow())
            .numeroIdentidad(citaRequest.getNumeroIdentidad())
            .sexo(Sexo.valueOf(citaRequest.getSexo()))
            .fechaNacimiento(citaRequest.getFechaNacimiento())
            .dentista(dentistaRepository.findById(citaRequest.getDentistaId()).orElseThrow())
            .usuario(usuarioRepository.findById(citaRequest.getUsuarioId()).orElseThrow())
            .tratamiento(tratamientoRepository.findById(citaRequest.getTratamientoId()).orElseThrow())
            .build();
        citaRepository.save(cita);
    }

    public void actualizarCita(Long id, CitaRequest citaRequest) {
        Cita cita = citaRepository.findById(id).get();
        cita.setMonto(citaRequest.getMonto());
        cita.setHora(citaRequest.getHora());
        cita.setFecha(citaRequest.getFecha());
        cita.setNombres(citaRequest.getNombres());
        cita.setApellidoPaterno(citaRequest.getApellidoPaterno());
        cita.setApellidoMaterno(citaRequest.getApellidoMaterno());
        cita.setTipoDocumento(tipoDocumentoRepository.findByNombre(citaRequest.getTipoDocumento()).orElseThrow());
        cita.setNumeroIdentidad(citaRequest.getNumeroIdentidad());
        cita.setSexo(Sexo.valueOf(citaRequest.getSexo()));
        cita.setFechaNacimiento(citaRequest.getFechaNacimiento());
        cita.setDentista(dentistaRepository.findById(citaRequest.getDentistaId()).orElseThrow());
        cita.setUsuario(usuarioRepository.findById(citaRequest.getUsuarioId()).orElseThrow());
        cita.setTratamiento(tratamientoRepository.findById(citaRequest.getTratamientoId()).orElseThrow());
        citaRepository.save(cita);
    }
    public void eliminarCita(Long id){
        Cita cita = citaRepository.findById(id).get();
        cita.setEstado("Cancelada");
        citaRepository.save(cita);
    }
}
