package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Transaccion;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class TransaccionDAO implements TransaccionDAOInterface {
    @Override
    public void nuevaTransaccion(Session session, Transaccion transaccion) {
        session.beginTransaction();
        session.save(transaccion);

        session.getTransaction().commit();
    }

    @Override
    public void modificarTransaccion(Session session, Transaccion transaccion) {
        session.beginTransaction();
        session.update(transaccion);

        session.getTransaction().commit();
    }

    @Override
    public void borrarTransaccion(Session session, Transaccion transaccion) {
        session.beginTransaction();
        session.delete(transaccion);

        session.getTransaction().commit();
    }

    @Override
    public ArrayList<Transaccion> obtenerTransacciones(Session session) {
        List<Transaccion> listaTransacciones = session.createQuery("FROM Transaccion").getResultList();
        ArrayList<Transaccion> transacciones = new ArrayList<>(listaTransacciones);

        return transacciones;
    }

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
