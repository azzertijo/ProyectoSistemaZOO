/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
 * @author eruma
 */
public class CargarItinerarios {

    private IAdministrarItinerarios adminItinerarios;

    /**
     * Método para mostrar un mensaje en pantalla, recibe una cadena de texto la
     * cual es la que se desea mostrar en el mensaje informativo.
     */
    private void mostrarMensaje(String msj) {
        JOptionPane.showMessageDialog(null, msj, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public CargarItinerarios() {
        try {
            this.adminItinerarios = new AdministrarItinerariosFachada("ZOO");
        } catch (PersistenciaException ex) {
            Logger.getLogger(CargarItinerarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Itinerario> cargarItinerariosGuia(String id) {
        List<Itinerario> lista = new ArrayList<Itinerario>();
        try {
            lista = adminItinerarios.recuperarItinerarios(id);
        } catch (PersistenciaException ex) {
            Logger.getLogger(CargarItinerarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

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
