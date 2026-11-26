package com.adiuvare.repository;

import com.adiuvare.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    List<Evento> findByAsociacion_IdUsuario(Integer idAsociacion);
}