package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Juego;
import org.hibernate.Session;

import java.util.List;

public interface JuegoDAOInterface {
    List<Juego> cargarJuegosPorEtiqueta(Session session, String nombre);
}
