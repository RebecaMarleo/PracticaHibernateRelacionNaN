package com.example.practicahibernaterelacionnan.Modelo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Etiqueta")
public class Etiqueta {
    @Id
    @Column(name="idEtiqueta")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name="nombre")
    private String nombre;

    // al no usar cascade=CascadeType.ALL si borro una categoría los juegos que pertenecían a ella se mantienen ya que estoy excluyendo CascadeType.REMOVE
    // es importante porque los juegos pueden pertenecer a varias categorías y al eliminar una de ellas los juegos siguen perteneciendo a otras (o no pero se les puede asignar una nueva)
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinTable(name="etiqueta_juego",
            joinColumns = @JoinColumn(name="idEtiqueta"),
            inverseJoinColumns = @JoinColumn(name="idJuego"))
    private List<Juego> juegos;

    public Etiqueta() {
    }

    public Etiqueta(String nombre, List<Juego> juegos, int id) {
        this.id = id;
        this.nombre = nombre;
    }

    public Etiqueta(String nombre, List<Juego> juegos) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
