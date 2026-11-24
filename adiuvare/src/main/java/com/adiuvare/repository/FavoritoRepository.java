package com.adiuvare.repository;

import com.adiuvare.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito, Integer> {
    List<Favorito> findByVoluntario_IdUsuario(Integer idVoluntario);
    Optional<Favorito> findByVoluntario_IdUsuarioAndEvento_IdEvento(Integer idVoluntario, Integer idEvento);
    boolean existsByVoluntario_IdUsuarioAndEvento_IdEvento(Integer idVoluntario, Integer idEvento);
}