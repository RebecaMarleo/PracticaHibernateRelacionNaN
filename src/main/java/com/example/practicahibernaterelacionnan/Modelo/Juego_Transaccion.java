package com.example.practicahibernaterelacionnan.Modelo;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

import javax.persistence.*;

@Entity
@Table(name="Juego_Transaccion")
public class Juego_Transaccion {
    @Id
    @Column(name="idJuegoTransaccion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="idTransaccion", referencedColumnName = "idTransaccion")
    private Transaccion transaccion;

    @ManyToOne
    @JoinColumn(name="idJuego", referencedColumnName = "idJuego")
    private Juego juego;

    @Column(name="cantidad")
    private int cantidad;

    public Juego_Transaccion() {
    }

    public Juego_Transaccion(int id, Transaccion transaccion, Juego juego, int cantidad) {
        this.id = id;
        this.transaccion = transaccion;
        this.juego = juego;
        this.cantidad = cantidad;
    }

    public Juego_Transaccion(Transaccion transaccion, Juego juego, int cantidad) {
        this.transaccion = transaccion;
        this.juego = juego;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public ObservableValue<Number> idProperty() {
        IntegerProperty propId = new SimpleIntegerProperty(juego.getId());
        return propId;
    }

    public ObservableValue<String> tituloProperty() {
        StringProperty propTitulo = new SimpleStringProperty(juego.getTitulo());
        return propTitulo;
    }

    public ObservableValue<Double> precioVentaProperty() {
        DoubleProperty propPrecioVenta = new SimpleDoubleProperty(juego.getPrecioVenta());
        return propPrecioVenta.asObject();
    }

    public ObservableValue<Double> precioCompraProperty() {
        DoubleProperty propPrecioCompra = new SimpleDoubleProperty(juego.getPrecioCompra());
        return propPrecioCompra.asObject();
    }

    public ObservableValue<String> pegiProperty() {
        StringProperty propPegi = new SimpleStringProperty(juego.getPegi());
        return propPegi;
    }

    public ObservableValue<Number> cantidadProperty() {
        IntegerProperty propCantidad = new SimpleIntegerProperty(cantidad);
        return propCantidad;
    }
}
