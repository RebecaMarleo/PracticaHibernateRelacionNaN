package com.example.practicahibernaterelacionnan.Modelo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Cliente")
public class Cliente {
    @Id
    @Column(name="idCliente")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="correo")
    private String correo;

    @Column(name="contrasena")
    private String contrasena;

    @Column(name="nombre")
    private String nombre;

    // al no usar cascade=CascadeType.ALL si borro un cliente las transacciones que pertenecían a él se mantienen ya que estoy excluyendo CascadeType.REMOVE
    // no es importante pero creo que sería adecuado para las cuentas de la empresa
    @OneToMany(mappedBy = "cliente", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH })
    private List<Transaccion> transacciones;

    public Cliente() {
    }

    public Cliente(int id, String correo, String contrasena, String nombre) {
        this.id = id;
        this.correo = correo;
        this.contrasena = contrasena;
        this.nombre = nombre;
    }

    public Cliente(String correo, String contrasena, String nombre) {
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
}
