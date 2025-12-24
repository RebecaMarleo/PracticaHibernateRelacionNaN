package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Juego;
import org.hibernate.Session;

import java.util.List;

public class JuegoDAO implements JuegoDAOInterface {
    @Override
    public List<Juego> cargarJuegosPorEtiqueta(Session session, String nombre) {
        String query = "SELECT DISTINCT j FROM Juego j JOIN FETCH j.etiquetas e WHERE e.nombre = :nombre";
        List<Juego> listaJuegos = session.createQuery(query, Juego.class)
                .setParameter("nombre", nombre)
                .getResultList();

        return listaJuegos;
    }
}
