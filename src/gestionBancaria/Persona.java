/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionBancaria;

/**
 *
 * @author santimiquel
 */
public class Persona {
    
    // ATRIBUTOS CLASE PERSONA
    private final String NIF;
    private String nombre;

    // CONSTRUCTOR
    public Persona(String nif, String nombre) {
        this.NIF = nif;
        this.nombre = nombre;
    }
    
    // GETTERS Y SETTERS
    public String getNombre() {
        return nombre;
    }

    public String getNif() {
        return NIF;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
//    //COMPARA ESTA PERSONA EN LA QUE ESTAMOS CON OTRO OBJETO PERSONA (PARÁMETRO otraPersona)
//    public boolean igual(Persona otraPersona){
//        boolean resultado=false;
//        
//        if(NIF.equalsIgnoreCase(otraPersona.getNif())){
//            resultado=true;
//        }
//        return resultado;        
//
//    }
//    
     // METODO COMPARA DNI PERSONA CON OTRO DNI DADO POR USUARIO
     public boolean igual(String otroNif){
        boolean resultado=false;
        
        if(NIF.equalsIgnoreCase(otroNif)){
            resultado=true;
        }
        return resultado;
        
    }
    
    
    @Override
    public String toString(){
        return String.format("%s (%s)",nombre,NIF);
    }
}
