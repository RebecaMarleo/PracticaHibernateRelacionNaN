package com.example.practicahibernaterelacionnan.Util;

import java.net.URL;

public class R {

    public static URL getProperties(String name) {
        // File.separator es la barra hacia atrás "\" pero los archivos .jar necesitan usar la barra hacia delante "/" y da error
        return Thread.currentThread().getContextClassLoader().getResource("Configuracion/" + name);
//        return Thread.currentThread().getContextClassLoader().getResource("Configuracion" + File.separator + name);
    }

    public static URL getUI(String name) {
        // File.separator es la barra hacia atrás "\" pero los archivos .jar necesitan usar la barra hacia delante "/" y da error
        return Thread.currentThread().getContextClassLoader().getResource("ui/" + name);
//        return Thread.currentThread().getContextClassLoader().getResource("ui" + File.separator + name);
    }
}
