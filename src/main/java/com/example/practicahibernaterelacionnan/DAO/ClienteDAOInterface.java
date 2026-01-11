package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Cliente;
import org.hibernate.Session;

import java.util.ArrayList;

public interface ClienteDAOInterface {
    void nuevoCliente(Session session, Cliente cliente);

    void modificarCliente(Session session, Cliente cliente);

    void borrarCliente(Session session, Cliente cliente);

    ArrayList<Cliente> obtenerClientes(Session session);

    Cliente obtenerCliente(Session session, String correo);

    boolean comprobarCliente(Session session, String correo);

    boolean comprobarClienteModificar(Session session, String correo, int id);
}
