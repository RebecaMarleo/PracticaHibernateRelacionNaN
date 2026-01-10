package com.example.practicahibernaterelacionnan.DAO;

import com.example.practicahibernaterelacionnan.Modelo.Pegi;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PegiDAO implements PegiDAOInterface {
    public static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    ArrayList<Pegi> listaPegi = null;
    List<String> nombresPegi = new ArrayList<>();

    @Override
    public List<String> obtenerListaPegi() {
        try {
            listaPegi =
                    JSON_MAPPER.readValue(new File("src/main/resources/Datos/pegi.json"),
                            JSON_MAPPER.getTypeFactory().constructCollectionType
                                    (ArrayList.class, Pegi.class));
            for (Pegi pegi : listaPegi) {
                nombresPegi.add(pegi.getNombre());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nombresPegi;
    }

    @Override
    public Pegi obtenerPegi(String nombre) {
        Pegi pegiSeleccionado = null;
        for (Pegi pegi : this.listaPegi) {
            if(pegi.getNombre().equals(nombre)) {
                pegiSeleccionado = pegi;
            }
        }
        return pegiSeleccionado;
    }
}
