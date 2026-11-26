package com.adiuvare.controller;

import com.adiuvare.model.Usuario;
import com.adiuvare.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:4173"})
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @PutMapping("/{id}/foto")
    public ResponseEntity<?> actualizarFoto(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        String base64Image = body.get("fotoPerfil");

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setFotoPerfil(base64Image);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(Map.of("mensaje", "Foto actualizada correctamente", "fotoPerfil", base64Image));
    }
}