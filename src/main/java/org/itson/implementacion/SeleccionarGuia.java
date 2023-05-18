/**
 * Clase SeleccionarGuia.java creada el 16/05/2023.
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
 * Clase que posee los métodos para recuperar a los guias
 *
 * @author kim, marki, elmer, yorx
 */
public class SeleccionarGuia {

    /**
     * Atributo que nos ayuda a trarnos los métodos de la fachada del subsitema
     * de administrar itinerarios.
     */
    IAdministrarItinerarios administrarItinerario;

    /**
     * Constructor por defecto que crea la base de datos
     */
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

    /**
     * Método que recupera los guias registrados en la base de datos
     *
     * @return lista de guias
     */
    public List<Guia> recuperarGuias() {
        List<Guia> guias = new ArrayList();
        try {
            guias = administrarItinerario.recuperarGuias();
        } catch (PersistenciaException ex) {
            mostrarMensaje(ex.getMessage());
        }
        return guias;
    }
    
    /**
     * Método que inserta objetos dummies en la base de datos
     */
    public void insertarDummies(){
        try {
            administrarItinerario.insertarDummies();
        } catch (PersistenciaException ex) {
            mostrarMensaje(ex.getMessage());
        }
        
    }
}
