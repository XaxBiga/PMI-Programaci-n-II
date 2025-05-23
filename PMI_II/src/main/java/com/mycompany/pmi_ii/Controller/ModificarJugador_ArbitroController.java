/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pmi_ii.Controller;

import com.mycompany.pmi_ii.Model.Arbitro;
import com.mycompany.pmi_ii.Model.Jugador;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gqiroga
 */
public class ModificarJugador_ArbitroController {
    private List<Jugador> jugadores;
    private List<Arbitro> arbitros;
    
    public ModificarJugador_ArbitroController() {
        jugadores = new ArrayList<>();
        arbitros = new ArrayList<>();
    }
    public void modificarJugador(Jugador jugador, String posicion, int goles, int tarjetaAmarilla){
        jugador.SetPosicion(posicion);
        jugador.SetGoles(goles);
        jugador.SetTarjetasAmarillas(tarjetaAmarilla);
    }
}
