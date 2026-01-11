package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Transaccion;
import org.hibernate.Session;

import java.util.ArrayList;

public interface TransaccionDAOInterface {
    ArrayList<Transaccion> obtenerTransaccionesPorCliente(Session session, int idCliente);

    Transaccion obtenerTransaccion(Session session, int idTransaccion);
}
