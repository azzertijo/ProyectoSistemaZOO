/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.implementacion;

import ObjNegocio.Guia;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.itson.fachada.AdministrarItinerariosFachada;
import org.itson.fachada.IAdministrarItinerarios;
import org.itson.fachada.excepciones.PersistenciaException;

/**
 *
 * @author kim
 */
public class SeleccionarGuia {

    IAdministrarItinerarios administrarItinerario;

    public SeleccionarGuia() {

        try {
            this.administrarItinerario = new AdministrarItinerariosFachada("ZOO");
        } catch (PersistenciaException ex) {
            Logger.getLogger(RegistarItinerario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para mostrar un mensaje en pantalla, recibe una cadena de texto la
     * cual es la que se desea mostrar en el mensaje informativo.
     */
    private void mostrarMensaje(String msj) {
        JOptionPane.showMessageDialog(null, msj, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public List<Guia> recuperarGuias() {
        List<Guia> guias = new ArrayList();
        try {
            guias=administrarItinerario.recuperarGuias();
        } catch (PersistenciaException ex) {
          mostrarMensaje(ex.getMessage());
        }
        return guias;
    }
}
