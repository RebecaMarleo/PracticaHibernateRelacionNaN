package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Juego;
import com.example.practicahibernaterelacionnan.Modelo.Juego_Transaccion;
import org.hibernate.Session;

import java.util.ArrayList;

public interface Juego_TransaccionDAOInterface {
    void nuevoJuego_transaccion(Session session, Juego_Transaccion juego_transaccion);

    void borrarJuego_transaccion(Session session, Juego_Transaccion juego_transaccion);

    ArrayList<Juego_Transaccion> obtenerJuegos(Session session);

    ArrayList<Juego_Transaccion> obtenerJuegosPorTransaccion(Session session, int idTransaccion);
}
