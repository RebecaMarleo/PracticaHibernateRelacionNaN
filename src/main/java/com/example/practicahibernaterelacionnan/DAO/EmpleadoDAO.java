package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Empleado;
import org.hibernate.Session;

import java.util.List;

public class EmpleadoDAO implements EmpleadoDAOInterface {
    @Override
    public Empleado obtenerEmpleado(Session session, String correo) {
        String query = "FROM Empleado WHERE correo = :correo";
        Empleado empleado = session.createQuery(query, Empleado.class)
                .setParameter("correo", correo)
                .getSingleResult();

        return empleado;
    }

    @Override
    public boolean comprobarCredenciales(Session session, String correo, String contrasena) {
        String query = "FROM Empleado WHERE correo = :correo AND contrasena = :contrasena";
        List<Empleado> empleados = session.createQuery(query, Empleado.class)
                .setParameter("correo", correo)
                .setParameter("contrasena", contrasena)
                .getResultList();

        return !empleados.isEmpty();
    }
}
