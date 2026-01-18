package com.example.practicahibernaterelacionnan.Modelo;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="Empleado")
public class Empleado {
    @Id
    @Column(name="idEmpleado")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="correo")
    private String correo;

    @Column(name="contrasena")
    private String contrasena;

    @Column(name="nombre")
    private String nombre;

    // al no usar cascade=CascadeType.ALL si borro una categoría los juegos que pertenecían a ella se mantienen ya que estoy excluyendo CascadeType.REMOVE
    // es importante porque los juegos pueden pertenecer a varias categorías y al eliminar una de ellas los juegos siguen perteneciendo a otras (o no pero se les puede asignar una nueva)
    @OneToMany(mappedBy = "cliente", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH })
    private List<Transaccion> transacciones;

    public Empleado() {
    }

    public Empleado(int id, String correo, String contrasena, String nombre) {
        this.id = id;
        this.correo = correo;
        this.contrasena = contrasena;
        this.nombre = nombre;
    }

    public Empleado(String correo, String contrasena, String nombre) {
        this.correo = correo;
        this.contrasena = contrasena;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Empleado empleado = (Empleado) o;
        return getId() == empleado.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
