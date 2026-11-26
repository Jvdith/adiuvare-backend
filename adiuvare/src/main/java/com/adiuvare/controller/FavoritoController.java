package com.adiuvare.controller;

import com.adiuvare.model.Evento;
import com.adiuvare.model.Favorito;
import com.adiuvare.model.Voluntario;
import com.adiuvare.repository.EventoRepository;
import com.adiuvare.repository.FavoritoRepository;
import com.adiuvare.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favoritos")
@CrossOrigin(origins = "http://localhost:5173")
public class FavoritoController {

    @Autowired private FavoritoRepository favoritoRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private EventoRepository eventoRepository;

    @GetMapping("/voluntario/{idVoluntario}")
    public List<Favorito> listarPorVoluntario(@PathVariable Integer idVoluntario) {
        return favoritoRepository.findByVoluntario_IdUsuario(idVoluntario);
    }

    @PostMapping
    public ResponseEntity<?> toggle(@RequestBody Map<String, Integer> body) {
        Integer idVoluntario = body.get("idVoluntario");
        Integer idEvento = body.get("idEvento");

        var existente = favoritoRepository.findByVoluntario_IdUsuarioAndEvento_IdEvento(idVoluntario, idEvento);

        if (existente.isPresent()) {
            favoritoRepository.delete(existente.get());
            return ResponseEntity.ok(Map.of("favorito", false));
        } else {
            Voluntario vol = (Voluntario) usuarioRepository.findById(idVoluntario)
                    .orElseThrow(() -> new RuntimeException("Voluntario no encontrado"));
            Evento ev = eventoRepository.findById(idEvento)
                    .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

            Favorito fav = new Favorito();
            fav.setVoluntario(vol);
            fav.setEvento(ev);
            favoritoRepository.save(fav);
            return ResponseEntity.ok(Map.of("favorito", true));
        }
    }
}