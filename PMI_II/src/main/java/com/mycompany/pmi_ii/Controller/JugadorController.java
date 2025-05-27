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

    public void guardarJugador(String nombre, String apellido, Fecha fecha, String nacionalidad,
                                String club, String posicion, int goles, int amarilla, int roja) {
        if (contarJugadoresEnClub(club) >= 7) {
            JOptionPane.showMessageDialog(null,
                "El club " + club + " ya tiene 7 jugadores. No se pueden agregar más.",
                "Límite de jugadores", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (nombre == null || nombre.trim().isEmpty() ||
            apellido == null || apellido.trim().isEmpty() ||
            nacionalidad.equals("-") || club.equals("-") || posicion.equals("-")) {
            JOptionPane.showMessageDialog(null,
                "Todos los campos deben estar completos y válidos.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
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
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null,
                "Error al guardar el jugador: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
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