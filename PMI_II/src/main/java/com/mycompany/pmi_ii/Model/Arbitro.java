package com.mycompany.pmi_ii.Model;

public class Arbitro extends Persona{

    public int TarjetasSacadas;
    public String Internacional;


    //Constructo sin Parametros
    public Arbitro(){

        super();
        this.TarjetasSacadas = 0;
        this.Internacional = "Indefinido";
    }

    //Constructor con Parametros
    public Arbitro(Fecha fechanacimiento, String nombre , String apellido , String nacionalidad,int tarjetasSacadas , String internacional){

        super(fechanacimiento,nombre,apellido,nacionalidad);
        this.TarjetasSacadas = tarjetasSacadas;
        this.Internacional = internacional;
    }

    //Set's Modificadores
    public void SetTarjetasSacadas(int tarjetas){
        this.TarjetasSacadas = tarjetas;
    }

    public void SetInternacional(String internacional){
        this.Internacional = internacional;
    }

    //Get's Observadores
    public int GetTarjetasSacadas(){
        return TarjetasSacadas;
    }

    public String getInternacional(){
        return Internacional;
    }


    //ToString
    @Override
    public String toString(){
        return super.toString() + "\n"+TarjetasSacadas + "\n"+Internacional;
    }
}