package com.adiuvare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Integer idEvento;

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String titulo;

    @Size(max = 100)
    @Column(length = 100)
    private String descripcion;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @NotBlank(message = "La ubicación es obligatoria")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String ubicacion;


    @ManyToOne
    @JoinColumn(name = "id_asociacion", nullable = false)
    private Asociacion asociacion;


    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;


    @JsonIgnore
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<Inscripcion> inscripciones = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<Favorito> favoritos = new ArrayList<>();


    public Integer getIdEvento() { return idEvento; }
    public void setIdEvento(Integer idEvento) { this.idEvento = idEvento; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public Asociacion getAsociacion() { return asociacion; }
    public void setAsociacion(Asociacion asociacion) { this.asociacion = asociacion; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public List<Inscripcion> getInscripciones() { return inscripciones; }
    public void setInscripciones(List<Inscripcion> inscripciones) { this.inscripciones = inscripciones; }

    public List<Favorito> getFavoritos() { return favoritos; }
    public void setFavoritos(List<Favorito> favoritos) { this.favoritos = favoritos; }
}