package com.mycompany.pmi_ii.Model;

public class Jugador extends Persona {

    public String ClubActual;
    public String Posicion;
    public int Goles;
    public int TarjetasAmarillas;
    public int TarjetasRojas;

    //Constructor sin parametros
    public Jugador(String nombre, String apellido){
        this.Nombre = nombre;
        this.Apellido=apellido;
    }
    public Jugador(){

        super();

        this.ClubActual = "Sin Definir";
        this.Posicion = "Indefinida";
        this.Goles = 0;
        this.TarjetasAmarillas = 0;
        this.TarjetasRojas = 0;
    }

    //Constructo con Parametros
    public Jugador(Fecha fechanacimiento, String nombre , String apellido , String nacionalidad , String club,String posicion,int cantAmarillas,int cantRojas,int goles){

        super(fechanacimiento,nombre,apellido,nacionalidad);

        this.ClubActual = club;
        this.Posicion = posicion;
        this.Goles = goles;
        this.TarjetasAmarillas = cantAmarillas;
        this.TarjetasRojas = cantRojas;

    }

    //Set y Get de ClubActual
    public void SetClubActual(String club){
        this.ClubActual = club;
    }

    public String GetClubActual(){
        return ClubActual;
    }


    //Set y Get de Posicion
    public void SetPosicion(String posicion){
        this.Posicion = posicion;
    }

    public String GetPosicion(){
        return Posicion;
    }


    //Set y Get de TarjetasAmarillas
    public void SetTarjetasAmarillas(int tarjetasAmarillas){
        this.TarjetasAmarillas = tarjetasAmarillas;
    }

    public int GetTarjetasAmarillas(){
        return TarjetasAmarillas;
    }


    //Set y Get de TarjetasRojas
    public void SetTarjetasRojas(int tarjetasRojas){
        this.TarjetasRojas = tarjetasRojas;
    }

    public int GetTarjetasRojas(){
        return TarjetasRojas;
    }


    //Set y Get de Goles
    public void SetGoles(int goles){
        this.Goles = goles;
    }

    public int GetGoles(){
        return Goles;
    }


    //ToString
    @Override
    public String toString(){
        return super.toString() + "\n"+ClubActual+"\n"+Posicion+"\n"+Goles + "\n"+TarjetasAmarillas + "\n"+TarjetasRojas ;
    }

}