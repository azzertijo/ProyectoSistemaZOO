/**
 * Clase Validador.java creada el 16/05/2023.
 */
package org.itson.implementacion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que contiene expresiones regulares para comprobar los datos que ingresa
 * el usuario.
 *
 * @author kim, marki, elmer, yorx
 */
public class Validador {

    /**
     * Método que valida el formato de la hora con una expresion regular.
     *
     * @param hora el string que se quiere comparar la hora.
     * @return regresa verdadero si cumple con la sintaxis correcta.
     * @throws Exception si la sintaxis es incorrecta.
     */
    public boolean validaHora(String hora) throws Exception {
        String patron = "^([01]\\d|2[0-3]):[0-5]\\d$";
        Pattern p = Pattern.compile(patron);
        Matcher matcher = p.matcher(hora);
        if (matcher.matches()) {
            return true;
        } else {
            throw new Exception("Hora inválida.");
        }
    }

    /**
     * Método que valida si el usuario ingreso bien el nombre de un itinerario
     * mediante expresiones regulares, no acepta caracteres especiales.
     *
     * @param nombre string que se quiere comparar, representando el nombre.
     * @return regresa verdadero si cumple con la sintaxis correcta.
     * @throws Exception si la sintaxis es incorrecta.
     */
    public boolean validaNombreItinerario(String nombre) throws Exception {
        String patron = "^[a-zA-Z0-9]{1,50}$";
        Pattern p = Pattern.compile(patron);
        Matcher matcher = p.matcher(nombre);
        if (matcher.matches()) {
            return true;
        } else {
            throw new Exception("Nombre inválido.");
        }
    }

}
