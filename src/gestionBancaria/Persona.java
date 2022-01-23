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
    
    private final String NIF;
    private String nombre;

      
    public Persona(String nif, String nombre) {
        this.NIF = nif;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNif() {
        return NIF;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    //COMPARA ESTA PERSONA EN LA QUE ESTAMOS CON OTRO OBJETO PERSONA (PARÁMETRO otraPersona)
    public boolean igual(Persona otraPersona){
        boolean resultado=false;
        
        if(NIF.equalsIgnoreCase(otraPersona.getNif())){
            resultado=true;
        }
        return resultado;        
       //OTRA FORMA DE HACERLO: USANDO EL MÉTODO igual(String nif)
       //   return this.igual(person.getNif());
    }
    
     //COMPARA NIF DE ESTA PERSONA EN LA QUE ESTAMOS CON OTRO NIF
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
