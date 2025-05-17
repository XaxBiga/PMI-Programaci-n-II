package com.mycompany.pmi_ii.Model;

public class Fecha {

    public int Dia;
    public int Mes;
    public int Anio;

    //Constructor con paramentros
    public Fecha(int dia , int mes , int anio){

        this.Dia = dia;
        this.Mes = mes;
        this.Anio = anio;
    }

    //Constructor sin Paramentros
    public Fecha(){

        this.Dia = 1;
        this.Mes = 1;
        this.Anio = 2000;
    }

    //Get's Observadores
    public int GetDia(){
        return Dia;
    }

    public int GetMes(){
        return Mes;
    }

    public int GetAnio(){
        return Anio;
    }

    //Set's Modificadores
    public void SetDia(int dia){
        this.Dia = dia;
    }

    public void SetMes(int mes){
        this.Mes = mes;
    }

    public void SetAnio(int anio){
        this.Anio = anio;
    }

    @Override
    //ToString
    public String toString(){
        return "Anio :"+Anio+", Mes :"+Mes+", Dia :"+Dia;
    }
}
