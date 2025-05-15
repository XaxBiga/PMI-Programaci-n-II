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

    public void guardarJugador(String nombre, String apellido) {
        Jugador nuevoJugador = new Jugador();
        jugadores.add(nuevoJugador);
        System.out.println("Jugador guardado: " + nombre + " " + apellido);
    }
    public void guardarArbitro(String nombre, String apellido, Fecha fecha,
                               String nacionalidad, int tarjetas, String internacional) {
        Arbitro nuevoArbitro = new Arbitro();
        nuevoArbitro.SetNombre(nombre);
        nuevoArbitro.SetApellido(apellido);
        nuevoArbitro.SetFechaNacimiento(fecha);
        nuevoArbitro.SetNacionalidad(nacionalidad);
        nuevoArbitro.SetTarjetasSacadas(tarjetas);
        nuevoArbitro.SetInternacional(internacional);
        arbitros.add(nuevoArbitro);
        System.out.println("Árbitro guardado: " + nombre + " " + apellido);
    }
}