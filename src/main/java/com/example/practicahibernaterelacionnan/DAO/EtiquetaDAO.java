package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Etiqueta;
import com.example.practicahibernaterelacionnan.Modelo.Juego;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class EtiquetaDAO implements EtiquetaDAOInterface {
    @Override
    public void nuevaEtiqueta(Session session, Etiqueta etiqueta) {
        session.beginTransaction();
        session.save(etiqueta);

        session.getTransaction().commit();
    }

    @Override
    public void modificarEtiqueta(Session session, Etiqueta etiqueta) {
        session.beginTransaction();
        session.update(etiqueta);

        session.getTransaction().commit();
    }

    @Override
    public void borrarEtiqueta(Session session, Etiqueta etiqueta) {
        session.beginTransaction();
        session.delete(etiqueta);

        session.getTransaction().commit();
    }

    @Override
    public ArrayList<Etiqueta> obtenerEtiquetas(Session session) {
        List<Etiqueta> listaEtiquetas = session.createQuery("FROM Etiqueta").getResultList();
        ArrayList<Etiqueta> etiquetas = new ArrayList<>(listaEtiquetas);

        return etiquetas;
    }

    @Override
    public ArrayList<Etiqueta> obtenerEtiquetasPorJuego(Session session, int idJuego) {
        String query = "SELECT DISTINCT e FROM Etiqueta e JOIN FETCH e.juegos j WHERE j.id = :idJuego";
        List<Etiqueta> listaEtiquetas = session.createQuery(query, Etiqueta.class)
                .setParameter("idJuego", idJuego)
                .getResultList();
        ArrayList<Etiqueta> arrayEtiquetas = new ArrayList<>(listaEtiquetas);

        return arrayEtiquetas;
    }

    @Override
    public ArrayList<Etiqueta> obtenerEtiquetasPorJuegoRestantes(Session session, int idJuego) {
        String query = "SELECT DISTINCT e FROM Etiqueta e WHERE NOT EXISTS (" +
                "SELECT j FROM e.juegos j WHERE j.id = :idJuego" +
                ")";
        List<Etiqueta> listaEtiquetas = session.createQuery(query, Etiqueta.class)
                .setParameter("idJuego", idJuego)
                .getResultList();
        ArrayList<Etiqueta> arrayEtiquetas = new ArrayList<>(listaEtiquetas);

        return arrayEtiquetas;
    }

    @Override
    public Etiqueta obtenerEtiqueta(Session session, String nombre) {
        String query = "FROM Etiqueta WHERE nombre = :nombre";
        Etiqueta etiqueta = session.createQuery(query, Etiqueta.class)
                .setParameter("nombre", nombre)
                .getSingleResult();

        return etiqueta;
    }

    @Override
    public boolean comprobarEtiqueta(Session session, String nombre) {
        String query = "FROM Etiqueta WHERE nombre = :nombre";
        List<Etiqueta> etiqueta = session.createQuery(query, Etiqueta.class)
                .setParameter("nombre", nombre)
                .getResultList();

        return !etiqueta.isEmpty();
    }

    @Override
    public boolean comprobarEtiquetaModificar(Session session, String nombre, int id) {
        String query = "FROM Etiqueta WHERE nombre = :nombre";
        List<Etiqueta> etiqueta = session.createQuery(query, Etiqueta.class)
                .setParameter("nombre", nombre)
                .getResultList();
        if (!etiqueta.isEmpty()) {
            return etiqueta.getFirst().getId() != id;
        } else {
            return !etiqueta.isEmpty();
        }
    }
}
