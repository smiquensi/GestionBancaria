/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionBancaria;

import java.util.Calendar;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;


/**
 *
 * @author santimiquel
 */
public class Movimiento {
    private final LocalDateTime fecha;
    private final double importe;
    private final String concepto;

    //constructor
    public Movimiento(double importe, String concepto) {
        this.fecha = LocalDateTime.now();
        this.importe = importe;
        this.concepto = concepto;
        
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getImporte() {
        return importe;
    }

    @Override
    public String toString() {
        return "Movimiento{" + "fecha=" + fecha + ", importe=" + importe + 
                ", concepto=" + concepto + '}';
    }
    
}
