package com.example.practicahibernaterelacionnan.Util;

import com.example.practicahibernaterelacionnan.Modelo.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    static SessionFactory factory = null;
    static {
        Configuration cfg = new Configuration();
        cfg.configure(R.getProperties("hibernate.cfg.xml"));

        cfg.addAnnotatedClass(Cliente.class);
        cfg.addAnnotatedClass(Empleado.class);
        cfg.addAnnotatedClass(Etiqueta.class);
        cfg.addAnnotatedClass(Juego.class);
        cfg.addAnnotatedClass(Juego_Transaccion.class);
        cfg.addAnnotatedClass(Transaccion.class);

        factory = cfg.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static Session getSession() {
        return factory.openSession();
    }
}
