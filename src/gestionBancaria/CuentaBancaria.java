/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionBancaria;

import static gestionBancaria.MenuPrincipal.teclado;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author santimiquel
 */
public class CuentaBancaria {

    private String numCuenta;
    private Persona titular;
    private double saldo;
    private DecimalFormat formateador = new DecimalFormat("###,###.##€");

    private List<Movimiento> movimientos;
    private Set<Persona> titulares = new HashSet<>();

    private String recienEliminado;
    private String recienAnyadido;

    public CuentaBancaria(String ncuenta, Persona titular) {
        this.numCuenta = ncuenta;
        this.titular = titular;
        titulares.add(titular);
        saldo = 0;
        movimientos = new ArrayList<>();
    }

    
    public String getNumCuenta() {
        return numCuenta;
    }

    public Persona getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public String showSaldo() {
        return formateador.format(saldo);
    }

    public Set<Persona> getTitulares() {
        return titulares;
    }

    public String getRecienEliminado() {
        return recienEliminado;
    }

    public String getRecienAnyadido() {
        return recienAnyadido;
    }

    public boolean autorizar(String autorizadoDNI, String autorizadoNombre) {
        Persona autorizado = new Persona(autorizadoDNI, autorizadoNombre);
        boolean autorizadoAnyadido = false;
        if (titulares.toString().contains(autorizadoDNI)) {
            autorizadoAnyadido = false;
        } else {
            autorizadoAnyadido = titulares.add(autorizado);
            recienAnyadido = autorizado.toString();

        }
        return autorizadoAnyadido;

    }

    public boolean quitarAutorizado(String autorizadoDNI) {
        boolean autorizadoEliminado = false;
        for (Persona buscarAutorizado : titulares) {
            if (buscarAutorizado.getNif().equalsIgnoreCase(autorizadoDNI)) {
                autorizadoEliminado = titulares.remove(buscarAutorizado);
                recienEliminado = buscarAutorizado.toString();
                break;
            }
        }
        return autorizadoEliminado;

    }

    public boolean buscarAutorizado(String autorizadoDNI) {
        boolean autorizadoExiste = false;
        for (Persona buscarAutorizado : titulares) {
            if (buscarAutorizado.getNif().equalsIgnoreCase(autorizadoDNI)) {
                autorizadoExiste = true;
                break;
            }
        }
        return autorizadoExiste;
    }

    private boolean registrarMovimiento(double importe, String concepto, String dniIngreso) {
        Movimiento mov = new Movimiento(importe, concepto, dniIngreso); 
        return movimientos.add(mov);
    }

    public double ingresar(double importe, String concepto, String dniIngreso) {
        saldo += importe;
        registrarMovimiento(importe, concepto, dniIngreso);
        return saldo;
    }

    public double sacar(double importe, String concepto, String dniIngreso) {
        saldo = getSaldo() - importe;
        registrarMovimiento((importe * -1), concepto, dniIngreso);
        return saldo;
    }

    public String getMovimientos() { 
        String movimientoList = "";
        for (Movimiento movimiento : movimientos) {
            movimientoList += movimiento.toString() + "\n";
        }
        return movimientoList;
    }

    public String getIngresos() {
        String movPositivos = "";
        for (int i = 0; i < movimientos.size(); i++) {
            if (movimientos.get(i).getImporte() > 0) {
                movPositivos += movimientos.get(i).toString() + "\n";
            }
        }
        return movPositivos;
    }

    public String getRetiradas() {
        String movNegativos = "";
        for (int i = 0; i < movimientos.size(); i++) {
            if (movimientos.get(i).getImporte() < 0) {
                movNegativos += movimientos.get(i).toString() + "\n";
            }
        }
        return movNegativos;
    }

}
