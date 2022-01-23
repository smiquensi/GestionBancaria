/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionBancaria;

import java.util.Calendar;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author santimiquel
 */
public class Movimiento {
    private final LocalDateTime FECHA;
    private DateTimeFormatter fechaFormateada = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final double IMPORTE;
    private final String CONCEPTO;
    private String DNI;

    //constructor
    public Movimiento(double importe, String concepto, String dni) {
        this.FECHA = LocalDateTime.now();
        this.IMPORTE = importe;
        this.CONCEPTO = concepto;
        this.DNI = dni;

    }
//    public Movimiento(){
//        this.FECHA = LocalDateTime.now();   
//    }

    public LocalDateTime getFecha() {
        return FECHA;
    }

    public double getImporte() {
        return IMPORTE;
    }

    @Override
    public String toString() {
        return ("Movimiento ->  Fecha: " + this.FECHA.format(fechaFormateada) + "-> Importe: " + this.IMPORTE
                + "€ -> Concepto=" + this.CONCEPTO);
    }

}
