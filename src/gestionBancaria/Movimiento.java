/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionBancaria;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author santimiquel
 */
public class Movimiento {
    
    // ATRIBUTOS CLASE MOVIMIENTO
    private final LocalDateTime FECHA;
    private DateTimeFormatter fechaFormateada = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final double IMPORTE;
    private final String CONCEPTO;
    private String DNI;

    
    // CONSTRUCTOR

    public Movimiento(LocalDateTime FECHA, double IMPORTE, String CONCEPTO, String DNI) {
        this.FECHA = FECHA;
        this.IMPORTE = IMPORTE;
        this.CONCEPTO = CONCEPTO;
        this.DNI = DNI;
    }

    public Movimiento(double importe, String concepto, String dni) {
        this.FECHA = LocalDateTime.now();
        this.IMPORTE = importe;
        this.CONCEPTO = concepto;
        this.DNI = dni;
    }
    
     // GETTERS Y SETTERS
    public LocalDateTime getFecha() {
        return FECHA;
    }

    public double getImporte() {
        return IMPORTE;
    }

    // METODO TO STRING
    @Override
    public String toString() {
        return ("Movimiento ->  Fecha: " + this.FECHA.format(fechaFormateada) + "-> Importe: " + this.IMPORTE
                + "€ -> Concepto=" + this.CONCEPTO);
    }

}
