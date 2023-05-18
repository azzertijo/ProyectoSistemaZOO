/**
 * Clase Validador.java creada el 16/05/2023.
 */
package org.itson.implementacion;

import java.awt.TextField;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    /**
     * Método que se encarga de validar que un horario sea el correcto,
     * respetando un formato 24 horas, separando horas y minutos.
     *
     * @param evt evento KeyEvent
     * @param txtField del campo que quiere validarse
     * @param tiempoMax si es minutos se pone 60 y si son horas se ponen 24.
     */
    public void validarHorario(java.awt.event.KeyEvent evt, TextField txtField, int tiempoMax) {
        char num = evt.getKeyChar();
        if (!Character.isDigit(num)) {
            evt.consume();
            return;
        }
        if (!txtField.getText().equalsIgnoreCase("")) {
            if (txtField.getText().length() <= 1) {
                String numeroCompleto = txtField.getText() + String.valueOf(num);
                if (Integer.parseInt(numeroCompleto) >= tiempoMax) {
                    evt.consume();
                }
            } else {
                evt.consume();
            }
        }
    }

}
