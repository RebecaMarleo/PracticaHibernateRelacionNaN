package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Cliente;
import com.example.practicahibernaterelacionnan.Modelo.Empleado;
import org.hibernate.Session;

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
    public boolean comprobarCredenciales(String dni, String pass) {
        return true;
    }
}
