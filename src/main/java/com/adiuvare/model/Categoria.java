package com.adiuvare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 50)
    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria")
    private List<Evento> eventos = new ArrayList<>();

    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Evento> getEventos() { return eventos; }
    public void setEventos(List<Evento> eventos) { this.eventos = eventos; }
}