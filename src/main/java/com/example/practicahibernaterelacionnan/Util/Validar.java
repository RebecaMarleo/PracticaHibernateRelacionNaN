package com.example.practicahibernaterelacionnan.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validar {
    // comprueba que el correo tenga un patrón direccion@dominio.extension
    public static boolean validarCorreo(String correo) {
        String regex = "^([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correo);

        return matcher.matches();
    }

    public static boolean validarNombre(String nombre) {
        String regex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nombre);

        return matcher.matches();
    }
}
