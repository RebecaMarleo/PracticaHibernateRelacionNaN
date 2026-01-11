package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Etiqueta;
import com.example.practicahibernaterelacionnan.Modelo.Transaccion;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class TransaccionDAO implements TransaccionDAOInterface {
    @Override
    public ArrayList<Transaccion> obtenerTransaccionesPorCliente(Session session, int idCliente) {
        String query = "SELECT DISTINCT t FROM Transaccion t JOIN FETCH t.cliente c WHERE c.id = :idCliente";
        List<Transaccion> listaTransacciones = session.createQuery(query, Transaccion.class)
                .setParameter("idCliente", idCliente)
                .getResultList();
        ArrayList<Transaccion> arrayTransacciones= new ArrayList<>(listaTransacciones);

        return arrayTransacciones;
    }

    @Override
    public Transaccion obtenerTransaccion(Session session, int idTransaccion) {
        String query = "FROM Transaccion WHERE id = :idTransaccion";
        Transaccion transaccion = session.createQuery(query, Transaccion.class)
                .setParameter("idTransaccion", idTransaccion)
                .getSingleResult();

        return transaccion;
    }
}
