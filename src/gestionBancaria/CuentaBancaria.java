/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionBancaria;

import static gestionBancaria.MenuPrincipal.teclado;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Scanner;

/**
 *
 * @author santimiquel
 */
public class CuentaBancaria {

    private String numCuenta;
    private Persona titular;
    private double saldo;
    private final int SALDO_MINIMO = -50;
    private final int MOVIMIENTO_GRANDE = 3000;
    private final String AVISO_NEGATIVO = "AVISO: Saldo negativo";
    private final String AVISO_HACIENDA = "AVISO: Notificación a Hacienda";
    private  Scanner teclado = new Scanner(System.in);
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

    public boolean autorizar() {// revisar si esto vale para hacer la parte de otros titulares
        String autorizadoDNI, autorizadoNombre;
        System.out.println("Indique el DNI del nuevo autorizado: ");
        autorizadoDNI = teclado.nextLine();
        System.out.println("Indique el nombre del nuevo autorizado: ");
        autorizadoNombre = teclado.nextLine();
        Persona autorizado = new Persona(autorizadoDNI, autorizadoNombre);
        System.out.println("El nuevo autorizado se ha añadido correctamente");
        System.out.println("En esta cuenta hay " + (autorizados.size() + 1) + " personas autorizadas.\n");
        return autorizados.add(autorizado); // hadcer pruebas para ver como eliminar a los titulares.
    }

    public boolean autorizar(String autorizadoDNI, String autorizadoNombre) {
        Persona autorizado = new Persona(autorizadoDNI, autorizadoNombre);
        System.out.println("El nuevo autorizado se ha añadido correctamente");
        System.out.println("En esta cuenta hay " + (autorizados.size() + 1) + " personas autorizadas.\n");
        return autorizados.add(autorizado);
    }

    public boolean quitarAutorizado() {
        if (autorizados.size() > 1) {
            System.out.println("Indique el DNI de la persona que quiere eliminar: ");
            String autorizadoDNI = teclado.nextLine();
            System.out.println("La persona autorizada se ha eliminado correctamente.");
            System.out.println("En esta cuenta hay " + (autorizados.size() - 1) + " personas autorizadas.\n");
            return autorizados.remove(autorizadoDNI);
        } else {
            System.out.println("No puede haber menos de un titular.\n");
            return false;
        }

    }

    public String verAutorizados() {
        //Se mostrara el toString() de cada objeto Persona que hay en el atributo autorizados
        if (autorizados.size() > 1) {
            return "\nPersonas autorizadas: " + autorizados;
        }
        return "";
    }

//    private double hacerMovimiento(double importe, String concepto) {
//        movimientos.add(importe, concepto);
//        return saldo;
//
//    }

    private boolean registrarMovimiento(double importe, String concepto) {
        Movimiento mov = new Movimiento(importe, concepto); // no se si tengo  q añadir el new Date
        return movimientos.add(mov);
        }

    public void ingresar(double importe) { // meter trycatch para texto 

        if (importe >= 0) {
            if (importe >= MOVIMIENTO_GRANDE) {
                System.out.println(AVISO_HACIENDA);
            }
            saldo += importe;
            System.out.println("Indique el concepto para esta transacción: ");
            registrarMovimiento(importe, teclado.nextLine());
            System.out.println("**Ingreso realizado con éxito**\n");
        } else {
            System.out.println("Introduzca un importe igual o superior a 0\n");
        }

    }

    public boolean sacar(double importe) { // meter trycatch para texto 
        double nuevoSaldo = getSaldo() - importe;

        if (importe >= 0) {
            if (nuevoSaldo < SALDO_MINIMO) {
                System.out.println("No se permite tener un saldo por debajo de -50\n");
                return false;
            }
            if (nuevoSaldo < 0) {
                System.out.println(AVISO_NEGATIVO);
            }
            if (importe >= MOVIMIENTO_GRANDE) {
                System.out.println(AVISO_HACIENDA);
            }
            saldo = nuevoSaldo;
            System.out.println("Indique el concepto para esta transacción: ");
            registrarMovimiento((importe * -1), teclado.nextLine());
            System.out.println("**Retirada realizada con éxito**\n");
            return true;
        } else {
            System.out.println("Introduzca un importe igual o superior a 0\n");
            return false;
        }
    }

    public String getMovimientos() {
        String movimientoList = null;
        for (Movimiento movimiento : movimientos) {
            movimientoList = movimiento + ", ";
        }
        return movimientoList;
    }

    public String informacionCuenta() { // falta los demas autorizados
        String infoCliente = ("nº de Cuenta: " + this.numCuenta + "\nNombre titular : "
                + this.titular + verAutorizados()
                + "\nSaldo : " + getSaldo() + "€\n");
        return infoCliente;
    }
}
