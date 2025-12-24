package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Etiqueta;
import org.hibernate.Session;

import java.util.ArrayList;

public interface EtiquetaDAOInterface {
    void nuevaEtiqueta(Session session, Etiqueta etiqueta);

    void modificarEtiqueta(Session session, Etiqueta etiqueta);

    void borrarEtiqueta(Session session, Etiqueta etiqueta);

    ArrayList<Etiqueta> obtenerEtiquetas(Session session);

    Etiqueta obtenerEtiqueta(Session session, String nombre);

    boolean comprobarEtiqueta(Session session, String nombre);
}
