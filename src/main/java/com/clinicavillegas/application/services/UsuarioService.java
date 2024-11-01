package com.clinicavillegas.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
