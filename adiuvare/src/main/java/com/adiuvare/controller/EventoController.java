package com.adiuvare.controller;

import com.adiuvare.model.Asociacion;
import com.adiuvare.model.Categoria;
import com.adiuvare.model.Evento;
import com.adiuvare.repository.CategoriaRepository;
import com.adiuvare.repository.EventoRepository;
import com.adiuvare.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "http://localhost:5173")
public class EventoController {

    @Autowired private EventoRepository eventoRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Evento> listar() {
        return eventoRepository.findAll();
    }

    @GetMapping("/asociacion/{idAsociacion}")
    public List<Evento> listarPorAsociacion(@PathVariable Integer idAsociacion) {
        return eventoRepository.findByAsociacion_IdUsuario(idAsociacion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Integer id) {
        return eventoRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> body) {
        try {
            // 1. Validar campos obligatorios
            Object idAsocObj = body.get("idAsociacion");
            Object idCatObj = body.get("idCategoria");
            String titulo = (String) body.get("titulo");
            String fechaHoraStr = (String) body.get("fechaHora");
            String ubicacion = (String) body.get("ubicacion");

            if (idAsocObj == null || idCatObj == null || titulo == null || fechaHoraStr == null || ubicacion == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Faltan campos obligatorios"));
            }

            Integer idAsociacion = ((Number) idAsocObj).intValue();
            Integer idCategoria = ((Number) idCatObj).intValue();

            // 2. Cargar la asociación (verificando que existe y es del tipo correcto)
            var usuarioOpt = usuarioRepository.findById(idAsociacion);
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Asociación no encontrada"));
            }
            if (!(usuarioOpt.get() instanceof Asociacion)) {
                return ResponseEntity.badRequest().body(Map.of("error", "El usuario no es una asociación"));
            }
            Asociacion asoc = (Asociacion) usuarioOpt.get();

            // 3. Cargar la categoría REAL de la BD (esto era el bug)
            Categoria cat = categoriaRepository.findById(idCategoria)
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

            // 4. Crear el evento
            Evento evento = new Evento();
            evento.setTitulo(titulo);
            evento.setDescripcion((String) body.getOrDefault("descripcion", ""));
            evento.setUbicacion(ubicacion);
            evento.setFechaHora(LocalDateTime.parse(fechaHoraStr));
            evento.setAsociacion(asoc);
            evento.setCategoria(cat);

            Evento guardado = eventoRepository.save(evento);
            System.out.println("Evento creado: " + guardado.getIdEvento() + " - " + guardado.getTitulo());
            return ResponseEntity.ok(guardado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage() != null ? e.getMessage() : "Error al crear evento"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Evento datos) {
        return eventoRepository.findById(id).map(evento -> {
            evento.setTitulo(datos.getTitulo());
            evento.setDescripcion(datos.getDescripcion());
            evento.setUbicacion(datos.getUbicacion());
            evento.setFechaHora(datos.getFechaHora());
            return ResponseEntity.ok(eventoRepository.save(evento));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        eventoRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("ok", true));
    }
}