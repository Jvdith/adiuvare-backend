package com.adiuvare.controller;

import com.adiuvare.model.Usuario;
import com.adiuvare.security.JwtService;
import com.adiuvare.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestBody Map<String, String> body) {
        String correo = body.get("correo");
        boolean existe = authService.existeCorreo(correo);
        return ResponseEntity.ok(Map.of("existe", existe));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Usuario usuario) {
        if (authService.existeCorreo(usuario.getCorreo())) {
            return ResponseEntity.badRequest().body(Map.of("error", "El correo ya está registrado"));
        }

        Usuario nuevoUsuario = authService.registrarUsuario(usuario);
        String token = jwtService.generarToken(nuevoUsuario);

        nuevoUsuario.setContrasena(null);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "usuario", nuevoUsuario
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String correo = body.get("correo");
        String contrasena = body.get("contrasena");

        return authService.validarCredenciales(correo, contrasena)
                .map(usuario -> {
                    String token = jwtService.generarToken(usuario);
                    usuario.setContrasena(null);
                    return ResponseEntity.ok(Map.of(
                            "token", token,
                            "usuario", usuario
                    ));
                })
                .orElseGet(() -> ResponseEntity.status(401).body(Map.of("error", "Credenciales incorrectas")));
    }
}