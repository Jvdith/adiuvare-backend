package com.adiuvare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "asociacion")
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Asociacion extends Usuario {



    @JsonIgnore
    @OneToMany(mappedBy = "asociacion", cascade = CascadeType.ALL)
    private List<Evento> eventos = new ArrayList<>();

    public List<Evento> getEventos() { return eventos; }
    public void setEventos(List<Evento> eventos) { this.eventos = eventos; }
}