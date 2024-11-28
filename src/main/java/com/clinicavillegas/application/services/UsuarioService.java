package com.clinicavillegas.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.dto.UsuarioRequest;
import com.clinicavillegas.application.dto.UsuarioResponse;
import com.clinicavillegas.application.models.Usuario;
import com.clinicavillegas.application.repositories.UsuarioRepository;
import com.clinicavillegas.application.specifications.UsuarioSpecification;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioResponse> obtenerClientes(String nombre, String rol) {
        Specification<Usuario> specs = UsuarioSpecification.conEstado(true)
                .and(UsuarioSpecification.conRol(rol))
                .and(UsuarioSpecification.conNombres(nombre));
        List<Usuario> usuarios = usuarioRepository.findAll(specs);
        return usuarios.stream().map(usuario ->
            UsuarioResponse.builder()
                .id(usuario.getId())
                .nombres(usuario.getNombres())
                .apellidoPaterno(usuario.getApellidoPaterno())
                .apellidoMaterno(usuario.getApellidoMaterno())
                .numeroIdentidad(usuario.getNumeroIdentidad())
                .sexo(usuario.getSexo())
                .telefono(usuario.getTelefono())
                .fechaNacimiento(usuario.getFechaNacimiento())
                .correo(usuario.getCorreo())
                .imagenPerfil(usuario.getImagenPerfil())
                .estado(usuario.isEstado())
                .imagenPerfil(usuario.getImagenPerfil())
                .rol(usuario.getRol())
                .tipoDocumento(usuario.getTipoDocumento())
                .fechaCreacion(usuario.getFechaCreacion())
                .fechaModificacion(usuario.getFechaModificacion())
                .build()
        ).toList();
    }

    public UsuarioResponse obtenerClientePorId(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombres(usuario.getNombres())
                .apellidoPaterno(usuario.getApellidoPaterno())
                .apellidoMaterno(usuario.getApellidoMaterno())
                .numeroIdentidad(usuario.getNumeroIdentidad())
                .sexo(usuario.getSexo())
                .telefono(usuario.getTelefono())
                .fechaNacimiento(usuario.getFechaNacimiento())
                .correo(usuario.getCorreo())
                .imagenPerfil(usuario.getImagenPerfil())
                .estado(usuario.isEstado())
                .imagenPerfil(usuario.getImagenPerfil())
                .rol(usuario.getRol())
                .tipoDocumento(usuario.getTipoDocumento())
                .fechaCreacion(usuario.getFechaCreacion())
                .fechaModificacion(usuario.getFechaModificacion())
                .build();
    }

    public void actualizarClientePorId(Long id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id).get();
        usuario.setNombres(request.getNombres());
        usuario.setApellidoPaterno(request.getApellidoPaterno());
        usuario.setApellidoMaterno(request.getApellidoMaterno());
        usuario.setTelefono(request.getTelefono());
        usuario.setImagenPerfil(request.getImagenPerfil());
        usuarioRepository.save(usuario);
    }

    public void eliminarCliente(Long id) {
        Usuario usuario = usuarioRepository.findById(id).get();
        usuario.setEstado(false);
        usuarioRepository.save(usuario);
    }
    
}
