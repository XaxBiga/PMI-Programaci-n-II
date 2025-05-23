package com.mycompany.pmi_ii.Model;

public class Persona {

    public Fecha FechaNacimiento;
    public String Nombre;
    public String Apellido;
    public String Nacionalidad;


    //Constructor con Parametros
    public Persona(Fecha fechanacimiento, String nombre , String apellido , String nacionalidad){

        this.FechaNacimiento = fechanacimiento;
        this.Nombre = nombre;
        this.Apellido = apellido;
        this.Nacionalidad = nacionalidad;
    }

    //Constructor sin Parametros
    public Persona(){

        this.FechaNacimiento = new Fecha();
        this.Nombre = "";
        this.Apellido = "";
        this.Nacionalidad = "";
    }

    //Get's Observadores
    public Fecha GetFechaNacimiento(){
        return FechaNacimiento;
    }

    public String GetNombre(){
        return Nombre;
    }

    public String GetApellido(){
        return Apellido;
    }

    public String GetNacionalidad(){
        return Nacionalidad;
    }

    //Set's Modificadores

    public void SetFechaNacimiento(Fecha nuevaFecha){
        this.FechaNacimiento = nuevaFecha;
    }

    public void SetNombre(String nombre){
        this.Nombre = nombre;
    }

    public void SetApellido(String apellido){
        this.Apellido = apellido;
    }

    public void SetNacionalidad(String nacionalidad){
        this.Nacionalidad = nacionalidad;
    }

    //ToString
    @Override
    public String toString(){
        return Nombre+"\n"+Apellido+"\n"+Nacionalidad+"\n"+FechaNacimiento.toString();
    }
}