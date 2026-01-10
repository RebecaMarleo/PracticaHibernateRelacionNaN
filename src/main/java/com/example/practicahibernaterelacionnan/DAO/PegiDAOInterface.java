package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Pegi;

import java.util.List;

public interface PegiDAOInterface {
    List<String> obtenerListaPegi();

    Pegi obtenerPegi(String nombre);
}
