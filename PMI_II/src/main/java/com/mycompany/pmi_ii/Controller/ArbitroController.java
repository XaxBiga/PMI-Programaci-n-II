/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pmi_ii.Controller;

import com.mycompany.pmi_ii.Model.Arbitro;
import com.mycompany.pmi_ii.Model.Fecha;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author frana
 */
public class ArbitroController {
    
    private final List<Arbitro> listaArbitros;
    private Arbitro arbitro;
    
    //constructores
    
    public ArbitroController(){
        this.listaArbitros = new ArrayList<>();
    }
    // Constructor para un arbitro específico
    public ArbitroController(Arbitro arbitro) {
        this.arbitro = arbitro;
        this.listaArbitros = null;
    }
    public void initArbitro(){
        this.arbitro = new Arbitro();
    }
    public Arbitro getArbitro(){
        return arbitro;
    }
    //Setter
    public void setListaArbitros(Arbitro arb){
        listaArbitros.add(arb);
    }
    public void setNombreArbitro(String nombre){
        this.arbitro.SetNombre(nombre);
    }
    public void setApellidoArbitro(String apellido){
        this.arbitro.SetApellido(apellido);
    }
    public void setFechaNacimientoArbitro(int dia, int mes, int año){
        this.arbitro.SetFechaNacimiento(new Fecha(dia, mes, año));
    }
    public void setNacionalidadArbitro(String nacionalidad){
        this.arbitro.SetNacionalidad(nacionalidad);
    }
    public void setTarjetasSacadasArbitro(int tarjetas){
        this.arbitro.SetTarjetasSacadas(tarjetas);
    }
    public void setInternacionlArbitro(String internacional){
        this.arbitro.SetInternacional(internacional);
    }
    //Getter
    public String getNombreArbitro(){
        return arbitro.GetNombre();
    }
    public String getApellidoArbitro(){
        return arbitro.GetApellido();
    }
    public Fecha getFechaNacimientoArbitro(){
        return arbitro.GetFechaNacimiento();
    }
    public String getNacionalidad(){
        return arbitro.GetNacionalidad();
    }
    public int getTarjetasSacadasArbitro(){
        return arbitro.GetTarjetasSacadas();
    }
    public String getInternacionlArbitro(){
        return arbitro.getInternacional();
    }
    public List<Arbitro> getListaArbitros(){
        return listaArbitros;
    }
    
    //Metodo para buscar si El Arbitro Ingresado ya esta en la lista
    public boolean ArbitroRepetido(String nombre,String apellido){
        
        for(Arbitro aux : listaArbitros){
            
            if(aux.GetNombre().equalsIgnoreCase(nombre) && aux.GetApellido().equalsIgnoreCase(apellido)){
                
                return true;
            }
        }
        
        return false;
    }
     public void modificarArbitro(Arbitro arbitroModificado){
        System.out.println("Arbitro Modificado :"+ arbitroModificado.GetNombre());
    }
    
}
