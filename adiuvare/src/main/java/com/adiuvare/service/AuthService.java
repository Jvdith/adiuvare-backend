package com.adiuvare.service;

import com.adiuvare.model.Usuario;
import com.adiuvare.model.Asociacion;
import com.adiuvare.model.Voluntario;
import com.adiuvare.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean existeCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }


    public Usuario registrarUsuario(Usuario usuario) {

        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));


        if ("ASOCIACION".equalsIgnoreCase(usuario.getTipoUsuario())) {
            Asociacion asoc = new Asociacion();
            copiarCamposBase(usuario, asoc);
            return usuarioRepository.save(asoc);
        } else {
            Voluntario vol = new Voluntario();
            copiarCamposBase(usuario, vol);
            return usuarioRepository.save(vol);
        }
    }


    private void copiarCamposBase(Usuario origen, Usuario destino) {
        destino.setNombre(origen.getNombre());
        destino.setCorreo(origen.getCorreo());
        destino.setContrasena(origen.getContrasena());
        destino.setFotoPerfil(origen.getFotoPerfil());
        destino.setTipoUsuario(origen.getTipoUsuario());
    }

    public Optional<Usuario> validarCredenciales(String correo, String contrasena) {
        return usuarioRepository.findByCorreo(correo)
                .filter(user -> passwordEncoder.matches(contrasena, user.getContrasena()));
    }
}