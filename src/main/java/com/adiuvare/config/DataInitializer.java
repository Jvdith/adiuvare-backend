package com.adiuvare.config;

import com.adiuvare.model.Categoria;
import com.adiuvare.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private CategoriaRepository categoriaRepository;

    @Override
    public void run(String... args) {
        // Solo insertar categorías si no existen
        if (categoriaRepository.count() > 0) return;

        categoriaRepository.save(crearCategoria("Animales"));
        categoriaRepository.save(crearCategoria("Salud"));
        categoriaRepository.save(crearCategoria("Comida"));
    }

    private Categoria crearCategoria(String nombre) {
        Categoria c = new Categoria();
        c.setNombre(nombre);
        return c;
    }
}