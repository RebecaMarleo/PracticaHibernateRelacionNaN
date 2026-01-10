package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Etiqueta;
import com.example.practicahibernaterelacionnan.Modelo.Juego;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public interface JuegoDAOInterface {
    void nuevoJuego(Session session, Juego juego);

    void modificarJuego(Session session, Juego juego);

    void borrarJuego(Session session, Juego juego);

    ArrayList<Juego> obtenerJuegos(Session session);

    List<Juego> obtenerJuegosPorEtiqueta(Session session, String nombre);

    boolean comprobarJuego(Session session, String titulo);

    boolean comprobarJuegoModificar(Session session, String titulo, int id);
}
