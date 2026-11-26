package com.adiuvare.controller;

import com.adiuvare.model.Categoria;
import com.adiuvare.repository.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Categoria categoria) {
        if (categoriaRepository.existsByNombreIgnoreCase(categoria.getNombre())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Esta categoría ya existe"));
        }
        return ResponseEntity.ok(categoriaRepository.save(categoria));
    }
}