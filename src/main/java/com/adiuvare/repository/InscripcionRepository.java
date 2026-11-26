package com.adiuvare.repository;

import com.adiuvare.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {
    List<Inscripcion> findByVoluntario_IdUsuario(Integer idVoluntario);
    Optional<Inscripcion> findByVoluntario_IdUsuarioAndEvento_IdEvento(Integer idVoluntario, Integer idEvento);
    boolean existsByVoluntario_IdUsuarioAndEvento_IdEvento(Integer idVoluntario, Integer idEvento);
}