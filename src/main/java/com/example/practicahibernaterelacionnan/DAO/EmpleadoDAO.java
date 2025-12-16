package com.example.practicahibernaterelacionnan.DAO;

public class EmpleadoDAO implements EmpleadoDAOInterface {
    @Override
    public boolean comprobarCredenciales(String dni, String pass) {
        return true;
    }
}
