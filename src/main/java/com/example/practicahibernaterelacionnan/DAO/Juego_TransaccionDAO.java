package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Etiqueta;
import com.example.practicahibernaterelacionnan.Modelo.Juego_Transaccion;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class Juego_TransaccionDAO implements Juego_TransaccionDAOInterface {
    @Override
    public ArrayList<Juego_Transaccion> obtenerJuegosPorTransaccion(Session session, int idTransaccion) {
        String query = "SELECT jt FROM Juego_Transaccion jt JOIN FETCH jt.juego j JOIN FETCH jt.transaccion t WHERE t.id = :idTransaccion";
        List<Juego_Transaccion> listaJuego_transacciones = session.createQuery(query, Juego_Transaccion.class)
                .setParameter("idTransaccion", idTransaccion)
                .getResultList();
        ArrayList<Juego_Transaccion> arrayJuego_transacciones = new ArrayList<>(listaJuego_transacciones);

        return arrayJuego_transacciones;
    }
}
