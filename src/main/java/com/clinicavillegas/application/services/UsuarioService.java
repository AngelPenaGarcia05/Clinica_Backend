package com.clinicavillegas.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.dto.UsuarioRequest;
import com.clinicavillegas.application.models.Usuario;
import com.clinicavillegas.application.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerClientes() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerClientePorId(Long id){
        return usuarioRepository.findById(id).orElseThrow();
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
