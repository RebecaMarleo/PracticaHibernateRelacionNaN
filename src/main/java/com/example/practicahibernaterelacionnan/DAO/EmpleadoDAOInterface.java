package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Empleado;
import org.hibernate.Session;

public interface EmpleadoDAOInterface {
    Empleado obtenerEmpleado(Session session, String correo);

    boolean comprobarCredenciales(String dni, String pass);
}
