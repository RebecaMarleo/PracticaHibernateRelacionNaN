package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Cliente;
import com.example.practicahibernaterelacionnan.Modelo.Etiqueta;
import com.example.practicahibernaterelacionnan.Modelo.Juego;
import com.example.practicahibernaterelacionnan.Modelo.Juego_Transaccion;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class JuegoDAO implements JuegoDAOInterface {
    @Override
    public void nuevoJuego(Session session, Juego juego) {
        session.beginTransaction();
        session.save(juego);

        session.getTransaction().commit();
    }

    @Override
    public void modificarJuego(Session session, Juego juego) {
        session.beginTransaction();
        session.update(juego);

        session.getTransaction().commit();
    }

    @Override
    public void borrarJuego(Session session, Juego juego) {
        session.beginTransaction();
        session.delete(juego);

        session.getTransaction().commit();
    }

    @Override
    public ArrayList<Juego> obtenerJuegos(Session session) {
        List<Juego> listaJuegos = session.createQuery("FROM Juego").getResultList();
        ArrayList<Juego> juegos = new ArrayList<>(listaJuegos);

        return juegos;
    }

    @Override
    public List<Juego> obtenerJuegosPorEtiqueta(Session session, String nombre) {
        String query = "SELECT DISTINCT j FROM Juego j JOIN FETCH j.etiquetas e WHERE e.nombre = :nombre";
        List<Juego> listaJuegos = session.createQuery(query, Juego.class)
                .setParameter("nombre", nombre)
                .getResultList();

        return listaJuegos;
    }

    @Override
    public List<Juego> obtenerJuegosPorTransaccion(Session session, int idTransaccion) {
        String query = "SELECT DISTINCT j FROM Juego j JOIN FETCH j.juego_transacciones jt WHERE jt.transaccion.id = :idTransaccion";
        List<Juego> listaJuegos = session.createQuery(query, Juego.class)
                .setParameter("idTransaccion", idTransaccion)
                .getResultList();

        return listaJuegos;
    }

    @Override
    public Juego obtenerJuego(Session session, String titulo) {
        String query = "FROM Juego WHERE titulo = :titulo";
        Juego juego = session.createQuery(query, Juego.class)
                .setParameter("titulo", titulo)
                .getSingleResult();

        return juego;
    }

    @Override
    public boolean comprobarJuego(Session session, String titulo) {
        String query = "FROM Juego WHERE titulo = :titulo";
        List<Juego> juego = session.createQuery(query, Juego.class)
                .setParameter("titulo", titulo)
                .getResultList();

        return !juego.isEmpty();
    }

    @Override
    public boolean comprobarJuegoModificar(Session session, String titulo, int id) {
        String query = "FROM Juego WHERE titulo = :titulo";
        List<Juego> juego = session.createQuery(query, Juego.class)
                .setParameter("titulo", titulo)
                .getResultList();
        if (!juego.isEmpty()) {
            return juego.getFirst().getId() != id;
        } else {
            return !juego.isEmpty();
        }
    }
}
