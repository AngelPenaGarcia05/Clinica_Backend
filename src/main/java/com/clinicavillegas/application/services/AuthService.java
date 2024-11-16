package com.clinicavillegas.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.dto.JwtResponse;
import com.clinicavillegas.application.dto.LoginRequest;
import com.clinicavillegas.application.dto.RegisterRequest;
import com.clinicavillegas.application.models.Rol;
import com.clinicavillegas.application.models.Sexo;
import com.clinicavillegas.application.models.TipoDocumento;
import com.clinicavillegas.application.models.Usuario;
import com.clinicavillegas.application.repositories.TipoDocumentoRepository;
import com.clinicavillegas.application.repositories.UsuarioRepository;

@Service
public class AuthService {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    public JwtResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getContrasena())
        );
        UserDetails userDetails =  usuarioRepository.findByCorreo(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(userDetails);
        return JwtResponse.builder()
                .token(token)
                .build();
    }

    public JwtResponse register(RegisterRequest request) {
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findByAcronimo(request.getTipoDocumento()).orElseThrow();
        
        Usuario usuario = Usuario.builder()
                .correo(request.getCorreo())
                .tipoDocumento(tipoDocumento)
                .numeroIdentidad(request.getDocumento())
                .nombres(request.getNombres())
                .apellidoPaterno(request.getApellidoPaterno())
                .apellidoMaterno(request.getApellidoMaterno())
                .fechaNacimiento(request.getFechaNacimiento())
                .telefono(request.getTelefono())
                .estado(true)
                .rol(Rol.PACIENTE)
                .sexo(Sexo.valueOf(request.getSexo()))
                .contrasena(passwordEncoder.encode(request.getContrasena()))
                .build();

        usuarioRepository.save(usuario);

        String token = jwtService.getToken(usuario);
        return JwtResponse.builder()
                .token(token)
                .build();
    }
}
