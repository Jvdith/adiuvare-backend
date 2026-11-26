package com.adiuvare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "voluntario")
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Voluntario extends Usuario {

    @JsonIgnore
    @OneToMany(mappedBy = "voluntario", cascade = CascadeType.ALL)
    private List<Inscripcion> inscripciones = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "voluntario", cascade = CascadeType.ALL)
    private List<Favorito> favoritos = new ArrayList<>();

    public List<Inscripcion> getInscripciones() { return inscripciones; }
    public void setInscripciones(List<Inscripcion> inscripciones) { this.inscripciones = inscripciones; }

    public List<Favorito> getFavoritos() { return favoritos; }
    public void setFavoritos(List<Favorito> favoritos) { this.favoritos = favoritos; }
}