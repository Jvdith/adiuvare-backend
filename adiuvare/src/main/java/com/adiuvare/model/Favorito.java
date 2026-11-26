package com.adiuvare.model;

import jakarta.persistence.*;

@Entity
@Table(name = "favorito", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_voluntario", "id_evento"})
})
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_favorito")
    private Integer idFavorito;

    @ManyToOne
    @JoinColumn(name = "id_voluntario", nullable = false)
    private Voluntario voluntario;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    public Integer getIdFavorito() { return idFavorito; }
    public void setIdFavorito(Integer idFavorito) { this.idFavorito = idFavorito; }

    public Voluntario getVoluntario() { return voluntario; }
    public void setVoluntario(Voluntario voluntario) { this.voluntario = voluntario; }

    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }
}