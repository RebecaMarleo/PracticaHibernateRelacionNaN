package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Juego_Transaccion;
import org.hibernate.Session;

import java.util.ArrayList;

public interface Juego_TransaccionDAOInterface {
    ArrayList<Juego_Transaccion> obtenerJuegosPorTransaccion(Session session, int idTransaccion);
}
