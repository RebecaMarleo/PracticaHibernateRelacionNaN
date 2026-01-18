package com.example.practicahibernaterelacionnan.Modelo;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="Transaccion")
public class Transaccion {
    @Id
    @Column(name="idTransaccion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="idCliente", referencedColumnName = "idCliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name="idEmpleado", referencedColumnName = "idEmpleado")
    private Empleado empleado;

    @Column(name="total")
    private double total;

    @Column(name="tipo")
    private String tipo;

    @OneToMany(mappedBy = "transaccion", cascade = CascadeType.REMOVE)
    private Set<Juego_Transaccion> juego_transacciones = new HashSet<Juego_Transaccion>();

    public Transaccion() {
    }

    public Transaccion(Cliente cliente, Empleado empleado, double total, String tipo) {
        this.cliente = cliente;
        this.empleado = empleado;
        this.total = total;
        this.tipo = tipo;
    }

    public Transaccion(int id, Cliente cliente, Empleado empleado, double total, String tipo) {
        this.id = id;
        this.cliente = cliente;
        this.empleado = empleado;
        this.total = total;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Set<Juego_Transaccion> getJuegoTransacciones() {
        return juego_transacciones;
    }

    public ObservableValue<Number> idProperty() {
        IntegerProperty propId = new SimpleIntegerProperty(id);
        return propId;
    }

    public ObservableValue<String> clienteProperty() {
        StringProperty propCliente = new SimpleStringProperty(cliente.getNombre());
        return propCliente;
    }

    public ObservableValue<String> empleadoProperty() {
        StringProperty propEmpleado = new SimpleStringProperty(empleado.getNombre());
        return propEmpleado;
    }

    public ObservableValue<Double> totalProperty() {
        DoubleProperty propTotal = new SimpleDoubleProperty(total);
        return propTotal.asObject();
    }

    public ObservableValue<String> tipoProperty() {
        StringProperty propTipo = new SimpleStringProperty(tipo);
        return propTipo;
    }

    public void setJuegoTransacciones(Set<Juego_Transaccion> juego_transacciones) {
        this.juego_transacciones = juego_transacciones;
    }

    public void addJuegoTransaccion(Juego_Transaccion juego_transaccion) {
        this.juego_transacciones.add(juego_transaccion);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaccion that = (Transaccion) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
