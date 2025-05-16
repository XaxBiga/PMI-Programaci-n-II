/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pmi_ii.Controller;

import com.mycompany.pmi_ii.Model.Arbitro;
import com.mycompany.pmi_ii.Model.Fecha;
import com.mycompany.pmi_ii.Model.Jugador;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author gqiroga
 */
public class AgregarJugador_ArbitroController {
   
    private List<Jugador> jugadores;
    private List<Arbitro> arbitros;
    public AgregarJugador_ArbitroController() {
        jugadores = new ArrayList<>();
        arbitros = new ArrayList<>();
    }

    public void guardarJugador(String nombre, String apellido, Fecha fecha, String nacionalidad,String club, String posicion, int goles,int amarilla, int roja) {
        Jugador nuevoJugador = new Jugador();
        
        int cantidadEnEseClub = contarJugadoresEnClub(club);


        
        if (cantidadEnEseClub >= 7 ){
            JOptionPane.showMessageDialog(null,
            "El club " + club + " ya tiene 7 jugadores. No se pueden agregar más.",
            "Límite de jugadores",
            JOptionPane.WARNING_MESSAGE);
            return;
        }
        //CONTROL DE ERRORES
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El apellido no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } 
        if (nacionalidad.equals("-")){
            JOptionPane.showMessageDialog(null, "La nacionalidad no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (club.equals("-")){
            JOptionPane.showMessageDialog(null, "La casilla internacional no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (posicion.equals("-")){
            JOptionPane.showMessageDialog(null, "La posición no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        if (jugadores == null) {
            jugadores = new ArrayList<>();
        }
        try {
            nuevoJugador.SetNombre(nombre);
            nuevoJugador.SetApellido(apellido);
            nuevoJugador.SetFechaNacimiento(fecha);
            nuevoJugador.SetNacionalidad(nacionalidad);
            nuevoJugador.SetClubActual(club);
            nuevoJugador.SetPosicion(posicion);
            nuevoJugador.SetGoles(goles);
            nuevoJugador.SetTarjetasAmarillas(amarilla);
            nuevoJugador.SetTarjetasRojas(roja);
            jugadores.add(nuevoJugador);
            System.out.println("Jugador guardado: " + nombre + " " + apellido );
        }catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(null, "Error al guardar el árbitro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error al agregar árbitro: " + e.getMessage());
        }
    }
    public int contarJugadoresEnClub(String club) {
    int contador = 0;
    if (jugadores == null) return 0;
    for (Jugador j : jugadores) {
        if (j.GetClubActual().equals(club)) {
            contador++;
        }
    }
    return contador;
}
    public List<Jugador> getJugador() {
        return jugadores;
    }
    public void guardarArbitro(String nombre, String apellido, Fecha fecha,
                           String nacionalidad, int tarjetas, String internacional) {
    
    //CONTROL DE ERRORES
    if (nombre == null || nombre.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (apellido == null || apellido.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "El apellido no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    } 
    if (nacionalidad.equals("-")){
        JOptionPane.showMessageDialog(null, "La nacionalidad no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (internacional.equals("-")){
        JOptionPane.showMessageDialog(null, "La casilla internacional no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (arbitros == null) {
        arbitros = new ArrayList<>();
    }
    try {
        Arbitro nuevoArbitro = new Arbitro();
        nuevoArbitro.SetNombre(nombre);
        nuevoArbitro.SetApellido(apellido);
        nuevoArbitro.SetFechaNacimiento(fecha);
        nuevoArbitro.SetNacionalidad(nacionalidad);
        nuevoArbitro.SetTarjetasSacadas(tarjetas);
        nuevoArbitro.SetInternacional(internacional);

        arbitros.add(nuevoArbitro);
        System.out.println("Árbitro guardado: " + nombre + " " + apellido);

    } catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog(null, "Error al guardar el árbitro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        System.err.println("Error al agregar árbitro: " + e.getMessage());
    }
    
}   
    public List<Arbitro> getArbitros() {
        return arbitros;
    }
}