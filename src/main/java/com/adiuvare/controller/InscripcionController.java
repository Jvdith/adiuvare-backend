package com.adiuvare.controller;

import com.adiuvare.model.Evento;
import com.adiuvare.model.Inscripcion;
import com.adiuvare.model.Voluntario;
import com.adiuvare.repository.EventoRepository;
import com.adiuvare.repository.InscripcionRepository;
import com.adiuvare.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/inscripciones")
@CrossOrigin(origins = "http://localhost:5173")
public class InscripcionController {

    @Autowired private InscripcionRepository inscripcionRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private EventoRepository eventoRepository;

    @GetMapping("/voluntario/{idVoluntario}")
    public List<Inscripcion> listarPorVoluntario(@PathVariable Integer idVoluntario) {
        return inscripcionRepository.findByVoluntario_IdUsuario(idVoluntario);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> body) {
        Integer idVoluntario = (Integer) body.get("idVoluntario");
        Integer idEvento = (Integer) body.get("idEvento");

        if (inscripcionRepository.existsByVoluntario_IdUsuarioAndEvento_IdEvento(idVoluntario, idEvento)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Ya estás inscrito en este evento"));
        }

        Voluntario vol = (Voluntario) usuarioRepository.findById(idVoluntario)
                .orElseThrow(() -> new RuntimeException("Voluntario no encontrado"));
        Evento ev = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        Inscripcion ins = new Inscripcion();
        ins.setNombre((String) body.get("nombre"));
        ins.setCorreoContacto((String) body.get("correoContacto"));
        ins.setVoluntario(vol);
        ins.setEvento(ev);

        return ResponseEntity.ok(inscripcionRepository.save(ins));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        inscripcionRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("ok", true));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarInscripcion(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        Optional<Inscripcion> inscripcionOpt = inscripcionRepository.findById(id);

        if (inscripcionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Inscripcion inscripcion = inscripcionOpt.get();

        String nuevoNombre = body.get("nombre");
        String nuevoCorreo = body.get("correoContacto");

        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            inscripcion.setNombre(nuevoNombre.trim());
        }

        if (nuevoCorreo != null && !nuevoCorreo.trim().isEmpty()) {
            inscripcion.setCorreoContacto(nuevoCorreo.trim());
        }

        inscripcionRepository.save(inscripcion);

        return ResponseEntity.ok(Map.of("mensaje", "Inscripción actualizada", "inscripcion", inscripcion));
    }
}