/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.implementacion;

import ObjNegocio.Dias;
import ObjNegocio.Especie;
import ObjNegocio.Itinerario;
import java.util.List;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.bson.types.ObjectId;
import org.itson.fachada.AdministrarItinerariosFachada;
import org.itson.fachada.IAdministrarItinerarios;
import org.itson.fachada.excepciones.PersistenciaException;

/**
 *
 * @author kim
 */
public class RegistarItinerario {

    /**
     * Método para mostrar un mensaje en pantalla, recibe una cadena de texto la
     * cual es la que se desea mostrar en el mensaje informativo.
     */
    private void mostrarMensaje(String msj) {
        JOptionPane.showMessageDialog(null, msj, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    IAdministrarItinerarios administrarItinerario;

    public RegistarItinerario() {
        
        try {
            this.administrarItinerario = new AdministrarItinerariosFachada("ZOO");
        } catch (PersistenciaException ex) {
            Logger.getLogger(RegistarItinerario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    public Itinerario crearItinerario(float longitud, int maxVisitantes, int numEspecies, String nombre, ObjectId guia, List<Dias> dias, LocalTime horaInicio, LocalTime horaFin, List<String> espSeleccionadas) {
         List<Especie> especies= new ArrayList<Especie>();
         especies= cargarTablaRegistro(espSeleccionadas);
        Itinerario itinerarioRegistro = new Itinerario(longitud, maxVisitantes, numEspecies, nombre, guia, dias, horaInicio, horaFin, especies);
        if (itinerarioRegistro != null) {
            return itinerarioRegistro;
        }
        return null;

    }
    
    public void registarItinerario(Itinerario itinerario){
        try {
            administrarItinerario.registrarItinerario(itinerario);
        } catch (PersistenciaException ex) {
           mostrarMensaje(ex.getMessage());
        }
    }
    
    public List<Especie> cargarTablaRegistroCompleto(){
          List<Especie> especies = new ArrayList<Especie>();
        try {
           especies= administrarItinerario.recuperarDatosFormulario();
        } catch (PersistenciaException ex) {
            mostrarMensaje(ex.getMessage());
        }
        return especies;
     }
    
     public List<Especie> cargarTablaRegistro(List<String> espSeleccionadas){
          List<Especie> especies = new ArrayList<Especie>();
          List<Especie> listaFinal=new ArrayList<Especie>();
        try {
           especies= administrarItinerario.recuperarDatosFormulario();
           for(int i=0; i<especies.size();i++){
               /*
               if(!(espSeleccionadas.contains(especies.get(i).getNomEspanol()))){
                   especies.remove(i);
               }
                */
               if(espSeleccionadas.contains(especies.get(i).getNomEspanol())){
                   listaFinal.add(especies.get(i));
                //   especies.remove(i);
               }
           }
        } catch (PersistenciaException ex) {
            mostrarMensaje(ex.getMessage());
        }
         System.out.println(listaFinal);
        return listaFinal;
     }
}
