package com.mycompany.pmi_ii.Controller;

import com.mycompany.pmi_ii.Model.Fecha;
import com.mycompany.pmi_ii.Model.Jugador;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class JugadorController {

    private List<Jugador> jugadores;

    public JugadorController() {
        jugadores = new ArrayList<>();
    }
    public boolean guardarJugador(String nombre, String apellido, Fecha fecha, String nacionalidad,
                                String club, String posicion, int goles, int amarilla, int roja) {

        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (apellido == null || apellido.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El apellido no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (fecha == null || fecha.Dia == 0 || fecha.Mes == 0 || fecha.Anio == 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fecha válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (nacionalidad == null || nacionalidad.equals("-") || nacionalidad.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una nacionalidad.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (club == null || club.equals("-") || club.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un club.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (contarJugadoresEnClub(club) >= 7) {
            JOptionPane.showMessageDialog(null,
                "El club " + club + " ya tiene 7 jugadores. No se pueden agregar más.",
                "Límite de jugadores", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (posicion == null || posicion.equals("-") || posicion.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una posición.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            Jugador nuevoJugador = new Jugador();
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
            System.out.println("Jugador guardado: " + nombre + " " + apellido);
            return true;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null,
                "Error al guardar el jugador: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }


    public int contarJugadoresEnClub(String club) {
        int contador = 0;
        for (Jugador j : jugadores) {
            if (j.GetClubActual().equals(club)) {
                contador++;
            }
        }
        return contador;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void modificarJugador(Jugador jugadorModificado) {
        System.out.println("Jugador modificado: " + jugadorModificado.GetNombre());
    }
}