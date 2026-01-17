package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Juego_Transaccion;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Juego_TransaccionDAO implements Juego_TransaccionDAOInterface {
    @Override
    public void nuevoJuego_transaccion(Session session, Juego_Transaccion juego_transaccion) {
        session.beginTransaction();
        session.save(juego_transaccion);

        session.getTransaction().commit();
    }

    @Override
    public void borrarJuego_transaccion(Session session, Juego_Transaccion juego_transaccion) {
        session.beginTransaction();
        session.delete(juego_transaccion);

        session.getTransaction().commit();
    }

    @Override
    public ArrayList<Juego_Transaccion> obtenerJuegos(Session session) {
        List<Object[]> resultados = session.createQuery(
                "SELECT jt.juego.id, MIN(jt.id) FROM Juego_Transaccion jt GROUP BY jt.juego.id",
                Object[].class
        ).getResultList();

        List<Integer> idsJuegoTransaccion = resultados.stream()
                .map(resultado -> (Integer) resultado[1])
                .collect(Collectors.toList());

        List<Juego_Transaccion> listaJuegos = session.createQuery(
                        "SELECT jt FROM Juego_Transaccion jt JOIN FETCH jt.juego WHERE jt.id IN :ids",
                        Juego_Transaccion.class
                )
                .setParameter("ids", idsJuegoTransaccion)
                .getResultList();

        return new ArrayList<>(listaJuegos);
    }

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
