package com.example.practicahibernaterelacionnan.Util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class R {

    public static URL getProperties(String name) {
        return Thread.currentThread().getContextClassLoader().getResource("Configuracion" + File.separator + name);
    }

    public static URL getUI(String name) {
        return Thread.currentThread().getContextClassLoader().getResource("ui" + File.separator + name);
    }
}
