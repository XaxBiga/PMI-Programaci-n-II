/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pmi_ii.Controller;

import com.mycompany.pmi_ii.Model.Arbitro;
import com.mycompany.pmi_ii.Model.Fecha;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author frana
 */
public class ArbitroController {
    
    private List<Arbitro> arbitros;
    
    public ArbitroController(){
        arbitros = new ArrayList<>();
    }
    
    public boolean guardarArbitro(String nombre, String apellido, Fecha fecha,
                               String nacionalidad, int tarjetas, String internacional) {

        //CONTROL DE ERRORES
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El apellido no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        //Control de Arbitro Repetido
        if(ArbitroRepetido(nombre,apellido) == true){
            JOptionPane.showMessageDialog(null, "El Arbitro Ingresa ya Existe en la Lista.", "Error", JOptionPane.ERROR_MESSAGE);
           return false; 
        }
        if (nacionalidad.equals("-")){
            JOptionPane.showMessageDialog(null, "La nacionalidad no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (internacional.equals("-")){
            JOptionPane.showMessageDialog(null, "La casilla internacional no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (arbitros == null) {
            arbitros = new ArrayList<>();
        }
        try {
            Arbitro nuevoArbitro = new Arbitro();
            nuevoArbitro.SetNombre(nombre);
            nuevoArbitro.SetApellido(apellido);
            nuevoArbitro.SetFechaNacimiento(fecha);
            nuevoArbitro.SetNacionalidad(nacionalidad);
            nuevoArbitro.SetTarjetasSacadas(tarjetas);
            nuevoArbitro.SetInternacional(internacional);

            arbitros.add(nuevoArbitro);
            System.out.println("Árbitro guardado: " + nombre + " " + apellido);
            return true;

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el árbitro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error al agregar árbitro: " + e.getMessage());
            return false;
        }

    }
    
    //Metodo para buscar si El Arbitro Ingresado ya esta en la lista
    public boolean ArbitroRepetido(String nombre,String apellido){
        
        for(Arbitro aux : arbitros){
            
            if(aux.GetNombre().equalsIgnoreCase(nombre) && aux.GetApellido().equalsIgnoreCase(apellido)){
                
                return true;
            }
        }
        
        return false;
    }
    
    public List<Arbitro> getArbitros() {
        return arbitros;
    }
    
    
     public void modificarArbitro(Arbitro arbitroModificado){
        System.out.println("Arbitro Modificado :"+ arbitroModificado.GetNombre());
    }
    
}
