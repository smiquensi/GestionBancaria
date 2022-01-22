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
    private Set<Persona> autorizados = new HashSet<>();

    public CuentaBancaria(String ncuenta, Persona titular) {
        this.numCuenta = ncuenta;
        this.titular = titular;
        autorizados.add(titular);
        saldo = 0;
        movimientos = new ArrayList<>();
    }

    //NO HAY METODOS SET PORQUE NO PERMITIMOS MODIFICAR DATOS DESPUES DE CREAR EL OBJETO
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

    public Set<Persona> getAutorizados() {
        return autorizados;
    }

    public boolean autorizar(String autorizadoDNI, String autorizadoNombre) {
        Persona autorizado = new Persona(autorizadoDNI, autorizadoNombre);
        boolean autorizadoAnyadido = false;
        if (autorizados.toString().contains(autorizadoDNI)) {
            autorizadoAnyadido = false;
        } else {
            autorizadoAnyadido = autorizados.add(autorizado);

        }
        return autorizadoAnyadido;

    }

    public boolean quitarAutorizado(String autorizadoDNI) {
        boolean autorizadoEliminado = false;
        for (Persona buscarAutorizado : autorizados) {
            if (buscarAutorizado.getNif().equalsIgnoreCase(autorizadoDNI)) {
                autorizadoEliminado = autorizados.remove(buscarAutorizado);
            }
        }
        return autorizadoEliminado;
    }

    public String verAutorizados() {
        //Se mostrara el toString() de cada objeto Persona que hay en el atributo autorizados
        if (autorizados.size() > 1) {
            return "\nPersonas autorizadas: " + autorizados;
        }
        return "";
    }

    private boolean registrarMovimiento(double importe, String concepto) {
        Movimiento mov = new Movimiento(importe, concepto); // no se si tengo  q añadir el new Date
        return movimientos.add(mov);
    }

    public double ingresar(double importe, String concepto) {
        saldo += importe;
        registrarMovimiento(importe, concepto);
        return saldo;
    }

    public double sacar(double importe, String concepto) {
        saldo = getSaldo() - importe;
        registrarMovimiento((importe * -1), concepto);
        return saldo;
    }

    public String getMovimientos() { //revisar que este correcto esto
        String movimientoList = "";
        for (Movimiento movimiento : movimientos) {
            movimientoList += movimiento.toString() + "\n";
        }
        return movimientoList;
    }

//    public String informacionCuenta() {
//        String infoCliente = ("nº de Cuenta: " + this.numCuenta + "\nNombre titular : "
//                + this.titular + verAutorizados()
//                + "\nSaldo : " + getSaldo() + "€\n");
//        return infoCliente;
//    }
}
