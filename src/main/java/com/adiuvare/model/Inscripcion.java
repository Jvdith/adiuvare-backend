package com.adiuvare.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "inscripcion", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_voluntario", "id_evento"})
})
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscripcion")
    private Integer idInscripcion;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String nombre;

    @NotBlank
    @Email
    @Size(max = 100)
    @Column(name = "correo_contacto", nullable = false, length = 100)
    private String correoContacto;

    @ManyToOne
    @JoinColumn(name = "id_voluntario", nullable = false)
    private Voluntario voluntario;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    public Integer getIdInscripcion() { return idInscripcion; }
    public void setIdInscripcion(Integer idInscripcion) { this.idInscripcion = idInscripcion; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreoContacto() { return correoContacto; }
    public void setCorreoContacto(String correoContacto) { this.correoContacto = correoContacto; }

    public Voluntario getVoluntario() { return voluntario; }
    public void setVoluntario(Voluntario voluntario) { this.voluntario = voluntario; }

    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }
}