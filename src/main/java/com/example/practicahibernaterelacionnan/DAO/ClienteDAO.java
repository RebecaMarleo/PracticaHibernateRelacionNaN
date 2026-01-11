package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Cliente;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements ClienteDAOInterface {
    @Override
    public void nuevoCliente(Session session, Cliente cliente) {
        session.beginTransaction();
        session.save(cliente);

        session.getTransaction().commit();
    }

    @Override
    public void modificarCliente(Session session, Cliente cliente) {
        session.beginTransaction();
        session.update(cliente);

        session.getTransaction().commit();
    }

    @Override
    public void borrarCliente(Session session, Cliente cliente) {
        session.beginTransaction();
        session.delete(cliente);

        session.getTransaction().commit();
    }

    @Override
    public ArrayList<Cliente> obtenerClientes(Session session) {
        List<Cliente> listaClientes = session.createQuery("FROM Cliente").getResultList();
        ArrayList<Cliente> clientes = new ArrayList<>(listaClientes);

        return clientes;
    }

    @Override
    public Cliente obtenerCliente(Session session, String correo) {
        String query = "FROM Cliente WHERE correo = :correo";
        Cliente cliente = session.createQuery(query, Cliente.class)
                .setParameter("correo", correo)
                .getSingleResult();

        return cliente;
    }

    @Override
    public boolean comprobarCliente(Session session, String correo) {
        String query = "FROM Cliente WHERE correo = :correo";
        List<Cliente> cliente = session.createQuery(query, Cliente.class)
                .setParameter("correo", correo)
                .getResultList();

        return !cliente.isEmpty();
    }

    @Override
    public boolean comprobarClienteModificar(Session session, String correo, int id) {
        String query = "FROM Cliente WHERE correo = :correo";
        List<Cliente> cliente = session.createQuery(query, Cliente.class)
                .setParameter("correo", correo)
                .getResultList();
        if (!cliente.isEmpty()) {
            return cliente.getFirst().getId() != id;
        } else {
            return !cliente.isEmpty();
        }
    }
}
