/**
 * Clase CargarItinerario.java creada el 16/05/2023.
 */
package org.itson.implementacion;

import ObjNegocio.Itinerario;
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
 * @author kim, marki, elmer, yorx
 */
public class CargarItinerarios {

      /**
     * Atributo que nos ayuda a trarnos los métodos de la fachada
     * del subsitema de administrar itinerarios.
     */
    private IAdministrarItinerarios adminItinerarios;

    /**
     * Método para mostrar un mensaje en pantalla, recibe una cadena de texto la
     * cual es la que se desea mostrar en el mensaje informativo.
     */
    private void mostrarMensaje(String msj) {
        JOptionPane.showMessageDialog(null, msj, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Constructor por defecto que especifica o crea la base con la cual se trabajara
     */
    public CargarItinerarios() {
        try {
            this.adminItinerarios = new AdministrarItinerariosFachada("ZOO");
        } catch (PersistenciaException ex) {
            Logger.getLogger(CargarItinerarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que consulta los itinerarios de cierto guía que existen la base para con 
     * ello poder cargar una tabla
     * @param id del guía que se quieren conocer los itinerarios
     * @return lista de itinerarios de un guía en especifico 
     */
    public List<Itinerario> cargarItinerariosGuia(String id) {
        List<Itinerario> lista = new ArrayList<Itinerario>();
        try {
            lista = adminItinerarios.recuperarItinerarios(id);
        } catch (PersistenciaException ex) {
            Logger.getLogger(CargarItinerarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    /**
     * Método que nos permite buscar itinerarios por un nombre especifico
     * @param nom nombre del itinerario que se desea recuperar
     * @return el itinerario que coincide con el nombre ingresado.
     */
    public Itinerario filtro(String nom) {
        Itinerario consultado = null;
        try {
            consultado = adminItinerarios.recuperarItinerariosPorNombre(nom);
        } catch (PersistenciaException ex) {
            mostrarMensaje(ex.getMessage());
        }
        return consultado;
    }
}
