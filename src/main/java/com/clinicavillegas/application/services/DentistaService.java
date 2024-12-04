package com.clinicavillegas.application.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.repositories.DentistaRepository;
import com.clinicavillegas.application.repositories.UsuarioRepository;
import com.clinicavillegas.application.specifications.DentistaSpecification;
import com.clinicavillegas.application.dto.DentistaRequest;
import com.clinicavillegas.application.dto.DentistaResponse;
import com.clinicavillegas.application.models.Dentista;
import com.clinicavillegas.application.models.Rol;
import com.clinicavillegas.application.models.Usuario;

@Service
public class DentistaService {
    @Autowired
    private DentistaRepository dentistaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Dentista> obtenerDentistas() {
        return dentistaRepository.findAll();
    }
    public Dentista obtenerDentista(Long id) {
        return dentistaRepository.findById(id).orElse(null);
    }
    public List<DentistaResponse> obtenerDentistas(String nombre, String especializacion, Long usuarioId) {
        Specification<Dentista> specs = DentistaSpecification.conNombre(nombre)
            .and(DentistaSpecification.conEspecializacion(especializacion))
            .and(DentistaSpecification.conEstado(true));
        List<Dentista> dentistas = dentistaRepository.findAll(specs);
        List<DentistaResponse> dentistasResponse = new ArrayList<>();
        for (Dentista dentista : dentistas) {
            dentistasResponse.add(DentistaResponse.builder()
                .id(dentista.getId())
                .nColegiatura(dentista.getNColegiatura())
                .especializacion(dentista.getEspecializacion())
                .usuarioId(dentista.getUsuario().getId())
                .nombres(dentista.getUsuario().getNombres())
                .correo(dentista.getUsuario().getCorreo())
                .estado(dentista.isEstado())
                .apellidoPaterno(dentista.getUsuario().getApellidoPaterno())
                .apellidoMaterno(dentista.getUsuario().getApellidoMaterno())
                .tipoDocumento(dentista.getUsuario().getTipoDocumento())
                .numeroIdentidad(dentista.getUsuario().getNumeroIdentidad())
                .sexo(dentista.getUsuario().getSexo().toString())
                .telefono(dentista.getUsuario().getTelefono())
                .fechaNacimiento(dentista.getUsuario().getFechaNacimiento().toString())
                .build());
        }
        return dentistasResponse;
    }
    public void agregarDentista(DentistaRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId()).orElseThrow();
        usuario.setRol(Rol.DENTISTA);
        usuarioRepository.save(usuario);
        Dentista dentista = Dentista.builder()
            .nColegiatura(request.getNColegiatura())
            .especializacion(request.getEspecializacion())
            .estado(true)
            .usuario(usuarioRepository.findById(request.getUsuarioId()).orElseThrow())
            .build();
        dentistaRepository.save(dentista);
    }

    public void actualizarDentista(Long id, DentistaRequest request) {
        Dentista dentista = dentistaRepository.findById(id).orElseThrow();
        Usuario usuarioAnterior = dentista.getUsuario();
        usuarioAnterior.setRol(Rol.PACIENTE);
        usuarioRepository.save(usuarioAnterior);
        Usuario usuarioActual = usuarioRepository.findById(request.getUsuarioId()).orElseThrow();
        dentista.setUsuario(usuarioActual);
        dentista.setNColegiatura(request.getNColegiatura());
        dentista.setEspecializacion(request.getEspecializacion());
        dentistaRepository.save(dentista);
    }

    public void eliminarDentista(Long id) {
        Dentista dentista = dentistaRepository.findById(id).get();
        dentista.setEstado(false);
        dentistaRepository.save(dentista);
    }
    public List<String> obtenerEspecialidades() {
        return dentistaRepository.findEspecializaciones();
    }
}
