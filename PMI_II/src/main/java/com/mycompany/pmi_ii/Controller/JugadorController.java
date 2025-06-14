package com.mycompany.pmi_ii.Controller;

import com.mycompany.pmi_ii.Model.Fecha;
import com.mycompany.pmi_ii.Model.Jugador;
import java.util.ArrayList;
import java.util.List;

public class JugadorController {

    private final List<Jugador> listaJugadores;
    
    private Jugador jugadorActual;
    
    public JugadorController() {
        this.listaJugadores = new ArrayList<>();
    }
    public JugadorController(List<Jugador> listaJugador) {
        this.listaJugadores = listaJugador;
        this.jugadorActual = null;
    }
    public JugadorController(Jugador jugador) {
        this.jugadorActual = jugador;
        this.listaJugadores = null;
    }
    public void nuevoJugador() {
        this.jugadorActual = new Jugador();
    }
    
    public Jugador getJugador(){
        return jugadorActual;
    }
    public void agregarAlalista(Jugador j){
        listaJugadores.add(j);
    }
    public void set_nombreJug(String nombre){
        jugadorActual.SetNombre(nombre);
    }
    public void set_apellidoJug(String apellido){
        jugadorActual.SetApellido(apellido);
    }
    public void set_FechanacimientoJug(int dia, int mes, int año){
        jugadorActual.SetFechaNacimiento(new Fecha(dia, mes, año));
    }
    public void set_NacionalidadJug(String nacionalidad){
        jugadorActual.SetNacionalidad(nacionalidad);
    }
    public void set_ClubActualJug(String ClubActual){
        jugadorActual.SetClubActual(ClubActual);
    }
    public void set_PosicionJug(String Posicion){
        jugadorActual.SetPosicion(Posicion);
    }
    public void set_GolesJug(int goles){
        jugadorActual.SetGoles(goles);
    }
    public void set_TarjetasAmarillasJug(int tarjetasA){
        jugadorActual.SetTarjetasAmarillas(tarjetasA);
    }
    public void set_TarjetasRojasJug(int tarjetasR){
        jugadorActual.SetTarjetasRojas(tarjetasR);
    }
    //hacer getter chi
    public String get_nombreJug(){
        return jugadorActual.GetNombre();
    }
    public String get_apellidoJug(){
        return jugadorActual.GetApellido();
    }
    public Fecha get_FechanacimientoJug(){
        return jugadorActual.GetFechaNacimiento();
    }
    public String get_NacionalidadJug(){
        return jugadorActual.GetNacionalidad();
    }
    public String get_ClubActualJug(){
        return jugadorActual.GetClubActual();
    }
    public String get_PosicionJug(){
        return jugadorActual.GetPosicion();
    }
    public int get_GolesJug(){
        return jugadorActual.GetGoles();
    }
    public int get_TarjetasAmarillasJug(){
        return jugadorActual.GetTarjetasAmarillas();
    }
    
    public int get_TarjetasRojasJug(){
        return jugadorActual.GetTarjetasRojas();
    }
    public List<Jugador> getListaJugadores() {
        return listaJugadores;
    }
    //Metodo para Verificar si el Jugador Ya esta en la lista
    public boolean JugadorRepetido(String nombre , String apellido){
    
        for(Jugador aux : listaJugadores){
            
            if(aux.GetNombre().equalsIgnoreCase(nombre) && aux.GetApellido().equalsIgnoreCase(apellido)){
                
                return true;
            }
            
        }
        return false;
    }
    
    public int contarJugadoresEnClub(String club) {
        int contador = 0;
        for (Jugador j : listaJugadores) {
            if (j.GetClubActual().equals(club)) {
                contador++;
            }
        }
        return contador;
    }

    public void modificarJugador(Jugador jugadorModificado) {
        System.out.println("Jugador modificado: " + jugadorModificado.GetNombre());
    }
}