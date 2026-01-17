package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Transaccion;
import org.hibernate.Session;

import java.util.ArrayList;

public interface TransaccionDAOInterface {
    void nuevaTransaccion(Session session, Transaccion transaccion);

    void modificarTransaccion(Session session, Transaccion transaccion);

    void borrarTransaccion(Session session, Transaccion transaccion);

    ArrayList<Transaccion> obtenerTransacciones(Session session);

    ArrayList<Transaccion> obtenerTransaccionesPorCliente(Session session, int idCliente);

    Transaccion obtenerTransaccion(Session session, int idTransaccion);
}
