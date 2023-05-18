/**
 * Clase RegistrarItinerario.java creada el 16/05/2023.
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
 * Clase que contiene los métodos necesarios para poder registrar itinerarios en
 * la base de datos
 *
 * @author kim, marki, elmer, yorx
 */
public class RegistarItinerario {

    /**
     * Método para mostrar un mensaje en pantalla, recibe una cadena de texto la
     * cual es la que se desea mostrar en el mensaje informativo.
     */
    private void mostrarMensaje(String msj) {
        JOptionPane.showMessageDialog(null, msj, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Atributo que nos ayuda a trarnos los métodos de la fachada del subsitema
     * de administrar itinerarios.
     */
    IAdministrarItinerarios administrarItinerario;

    /**
     * Constructor por defecto que especifica o crea la base de datos con la que
     * va a trabajar el programa
     */
    public RegistarItinerario() {

        try {
            this.administrarItinerario = new AdministrarItinerariosFachada("ZOO");
        } catch (PersistenciaException ex) {
            Logger.getLogger(RegistarItinerario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que se encarga de crear un objeto itinerario
     *
     * @param longitud la longitud de las zonas que tiene el itinerario
     * @param maxVisitantes el máximo de visitantes que puede tener un
     * itinerario
     * @param numEspecies la cantidad de especies que tiene un itinerario
     * @param nombre nombre del itinerario y que se considera unico
     * @param guia id del guia que atiende dicho itinerario
     * @param dias días que se realiza el itinerario
     * @param horaInicio hora de inicio del itinerario
     * @param horaFin hora fin del itinerario
     * @param espSeleccionadas lista de especies que el guia atendera en su
     * itinerario
     * @return objeto itinerario
     */
    public Itinerario crearItinerario(float longitud, int maxVisitantes, int numEspecies, String nombre, ObjectId guia, List<Dias> dias, LocalTime horaInicio, LocalTime horaFin, List<String> espSeleccionadas) {
        List<Especie> especies = new ArrayList<Especie>();
        especies = cargarTablaRegistro(espSeleccionadas);
        Itinerario itinerarioRegistro = new Itinerario(longitud, maxVisitantes, numEspecies, nombre, guia, dias, horaInicio, horaFin, especies);
        if (itinerarioRegistro != null) {
            return itinerarioRegistro;
        }
        return null;

    }

    /**
     * Método que permite registrar un itinerario en la base de datos y lanza
     * una expecion persistencia en caso de no poder
     *
     * @param itinerario itinerario que se desea registrar
     */
    public void registarItinerario(Itinerario itinerario) {
        try {
            administrarItinerario.registrarItinerario(itinerario);
            mostrarMensaje("El itinerario se registró");
        } catch (PersistenciaException ex) {
            mostrarMensaje(ex.getMessage());
        }
    }

    /**
     * Método que se encarga de recuperar las especies para mostrarlas en una
     * tabla al guía para que asi pueda seleccionarlas
     *
     * @return lista de especies disponibles en la base de datos
     */
    public List<Especie> cargarTablaRegistroCompleto() {
        List<Especie> especies = new ArrayList<Especie>();
        try {
            especies = administrarItinerario.recuperarDatosFormulario();
        } catch (PersistenciaException ex) {
            mostrarMensaje(ex.getMessage());
        }
        return especies;
    }

    /**
     * Método que transforma la lista string seleccionada por el guía en una
     * lista de objetos especies
     *
     * @param espSeleccionadas lista del nombre de las especies que el guía
     * seleccionó
     * @return lista de las especies seleccionadas por el usuario
     */
    public List<Especie> cargarTablaRegistro(List<String> espSeleccionadas) {
        List<Especie> especies = new ArrayList<Especie>();
        List<Especie> listaFinal = new ArrayList<Especie>();
        try {
            especies = administrarItinerario.recuperarDatosFormulario();
            for (int i = 0; i < especies.size(); i++) {
                if (espSeleccionadas.contains(especies.get(i).getNomEspanol())) {
                    listaFinal.add(especies.get(i));
                }
            }
        } catch (PersistenciaException ex) {
            mostrarMensaje(ex.getMessage());
        }
        System.out.println(listaFinal);
        return listaFinal;
    }

    /**
     * Método que permite construir las horas mediante la hora y minuto
     * recibidos en el parametro
     *
     * @param hora hora en formato 24 horas
     * @param minuto mintuos de la hora
     * @return hora en formato LocalTime
     */
    public LocalTime construirHoras(String hora, String minuto) {
        LocalTime horaInicio = LocalTime.of(Integer.parseInt(hora), Integer.parseInt(minuto));
        System.out.println(horaInicio);
        return horaInicio;
    }
    
}
