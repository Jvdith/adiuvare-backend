package com.adiuvare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo no es válido")
    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String contrasena;

    @Size(max = 500)
    @Column(name = "foto_perfil", length = 500)
    private String fotoPerfil;

    @NotBlank(message = "El tipo de usuario es obligatorio")
    @Column(name = "tipo_usuario", nullable = false, length = 20)
    private String tipoUsuario;

    @Column(length = 20)
    private String tema = "CLARO";

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
}