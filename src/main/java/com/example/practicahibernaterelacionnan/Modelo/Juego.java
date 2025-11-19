package com.example.practicahibernaterelacionnan.Modelo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Juego")
public class Juego {
    @Id
    @Column(name="idJuego")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="titulo")
    private String titulo;

    @Column(name="precio")
    private double precio;

    @Column(name="pegi")
    private String pegi;

    // al no usar cascade=CascadeType.ALL si borro una categoría los juegos que pertenecían a ella se mantienen ya que estoy excluyendo CascadeType.REMOVE
    // es importante porque los juegos pueden pertenecer a varias categorías y al eliminar una de ellas los juegos siguen perteneciendo a otras (o no pero se les puede asignar una nueva)
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinTable(name="etiqueta_juego",
            joinColumns = @JoinColumn(name="idJuego"),
            inverseJoinColumns = @JoinColumn(name="idEtiqueta"))
    private List<Etiqueta> etiquetas;

    @OneToMany(mappedBy = "juego", cascade = CascadeType.DETACH)
    private Set<Juego_Transaccion> juego_transacciones = new HashSet<Juego_Transaccion>();

    public Juego() {
    }

    public Juego(String titulo, double precio, String pegi) {
        this.titulo = titulo;
        this.precio = precio;
        this.pegi = pegi;
    }

    public Juego(int id, String titulo, double precio, String pegi) {
        this.id = id;
        this.titulo = titulo;
        this.precio = precio;
        this.pegi = pegi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getPegi() {
        return pegi;
    }

    public void setPegi(String pegi) {
        this.pegi = pegi;
    }

    public Set<Juego_Transaccion> getJuegoTransacciones() {
        return juego_transacciones;
    }

    public void setJuegoTransacciones(Set<Juego_Transaccion> juego_transacciones) {
        this.juego_transacciones = juego_transacciones;
    }

    public void addJuegoTransaccion(Juego_Transaccion juego_transaccion) {
        this.juego_transacciones.add(juego_transaccion);
    }
}
