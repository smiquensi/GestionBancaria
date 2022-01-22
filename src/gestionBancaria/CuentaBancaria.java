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
    private Set<Persona> titulares = new HashSet<>();

    public CuentaBancaria(String ncuenta, Persona titular) {
        this.numCuenta = ncuenta;
        this.titular = titular;
        titulares.add(titular);
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

    public Set<Persona> getTitulares() {
        return titulares;
    }

    public boolean autorizar(String autorizadoDNI, String autorizadoNombre) {
        Persona autorizado = new Persona(autorizadoDNI, autorizadoNombre);
        boolean autorizadoAnyadido = false;
        if (titulares.toString().contains(autorizadoDNI)) {
            autorizadoAnyadido = false;
        } else {
            autorizadoAnyadido = titulares.add(autorizado);

        }
        return autorizadoAnyadido;

    }

    public boolean quitarAutorizado(String autorizadoDNI) {
        boolean autorizadoEliminado = false;
        for (Persona buscarAutorizado : titulares) {
            if (buscarAutorizado.getNif().equalsIgnoreCase(autorizadoDNI)) {
                autorizadoEliminado = titulares.remove(buscarAutorizado);
                break;
            }
        }
        return autorizadoEliminado;

    }
// intentar meter este metodo dentro de los metodos quitarAutorizado y autorizar

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

//    public String verAutorizados() {
//        //Se mostrara el toString() de cada objeto Persona que hay en el atributo autorizados
//        if (titulares.size() > 1) {
//            return  "\nPersonas autorizadas: "+ titulares.toString();
//        }
//        return "";
//    }
    private boolean registrarMovimiento(double importe, String concepto, String dniIngreso) {
        Movimiento mov = new Movimiento(importe, concepto, dniIngreso); // no se si tengo  q añadir el new Date
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
