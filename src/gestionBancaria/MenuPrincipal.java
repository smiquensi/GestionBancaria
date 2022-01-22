/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionBancaria;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author santimiquel
 */
public class MenuPrincipal {

    static Scanner teclado = new Scanner(System.in);
    static Scanner sc = new Scanner(System.in); //creo un segundo scanner para que no se me duplique el menu al ingresar o sacar efectivo

    private static final int SALDO_MINIMO = -50;
    private static final int MOVIMIENTO_GRANDE = 3000;
    private static final String AVISO_NEGATIVO = "AVISO: Saldo negativo";
    private static final String AVISO_HACIENDA = "AVISO: Notificación a Hacienda";
    private static double importe;
    private static String concepto;

//
    public static void main(String[] args) {
        Persona titular = new Persona("44874926V", "Santi Miquel");
        CuentaBancaria cuenta = new CuentaBancaria("ES1234567890123456789012", titular);
        boolean bucleMenu = true;

        do {
            String respuesta = menu();
            switch (respuesta) {
                case "1":
                    System.out.println(informacionCuenta(cuenta));
                    break;
                case "2":
                    hacerIngreso(cuenta);
                    break;
                case "3":
                    hacerRetirada(cuenta);
                    break;
                case "4":
                    registrarAutorizado(cuenta);
                    break;
                case "5":
                    eliminarAutorizado(cuenta);
                    break;
                case "6":
                    System.out.println(cuenta.getMovimientos());                   
                    break;
                case "0":
                    bucleMenu = false;
                    System.out.println("Gracias por utilizar nuestros servicios.\n"
                            + "Le esperamos pronto.");
                    break;
                default:
                    System.out.println("Introduzca un numero del 0 al 6.");
            }
            if (!respuesta.equalsIgnoreCase("0")&& !respuesta.equalsIgnoreCase("1")) {
                System.out.println(informacionCuenta(cuenta));
            }
        } while (bucleMenu);

    }

    public static String menu() {
        String respuesta;
        System.out.println("GESTION DE CUENTA BANCARIA");
        System.out.println("---------------------------");
        System.out.println("¿Que transaccion desea realizar?");
        System.out.println("1 - Informacion de la cuenta.");
        System.out.println("2 - Ingresar dinero.");
        System.out.println("3 - Sacar dinero.");
        System.out.println("4 - Nuevo titular.");
        System.out.println("5 - Eliminar titular.");
        System.out.println("6 - Ver movimientos.");
        System.out.println("0 - Salir.");
        respuesta = sc.nextLine();
        return respuesta;
    }

    public static int hacerIngreso(CuentaBancaria cuenta) { // meter trycatch para texto 
        System.out.println("Indique el importe a ingresar: ");
        int resultadoIngreso;
        importe = teclado.nextDouble();
        if (importe >= 0) {
            if (importe >= MOVIMIENTO_GRANDE) {
                System.out.println(AVISO_HACIENDA);
                resultadoIngreso = 1;
            } else {
                resultadoIngreso = 0;
            }
            System.out.println("Indique el concepto para esta transacción: ");
            concepto = sc.nextLine();
            cuenta.ingresar(importe, concepto);
            System.out.println("**Ingreso realizado con éxito**\n");
        } else {
            System.out.println("Introduzca un importe igual o superior a 0\n");
            resultadoIngreso = -1;
        }
        return resultadoIngreso;
    }

    public static double hacerRetirada(CuentaBancaria cuenta) { // meter trycatch para texto 
        System.out.println("Indique el importe a retirar: ");
        importe = teclado.nextDouble();
        if (importe >= 0) {
            if (cuenta.getSaldo() - importe < SALDO_MINIMO) {
                System.out.println("No se permite tener un saldo por debajo de -50\n");
            } else {
                if (cuenta.getSaldo() - importe < 0) {
                    System.out.println(AVISO_NEGATIVO);
                }
                if (importe >= MOVIMIENTO_GRANDE) {
                    System.out.println(AVISO_HACIENDA);
                }
                System.out.println("Indique el concepto para esta transacción: ");
                concepto = sc.nextLine();
                cuenta.sacar(importe, concepto);
                System.out.println("**Retirada realizada con éxito**\n");
            }
        } else {
            System.out.println("Introduzca un importe igual o superior a 0\n");

        }
        return cuenta.getSaldo();
    }

    public static void registrarAutorizado(CuentaBancaria cuenta) {
        String autorizadoDNI, autorizadoNombre;
        System.out.println("Indique el DNI del nuevo autorizado: ");
        autorizadoDNI = teclado.nextLine();
        System.out.println("Indique el nombre del nuevo autorizado: ");
        autorizadoNombre = sc.nextLine();
        if (cuenta.autorizar(autorizadoDNI, autorizadoNombre)) {
            System.out.println("El nuevo autorizado se ha añadido correctamente");
            System.out.println("En esta cuenta hay " + (cuenta.getAutorizados().size()) + " personas autorizadas.\n");
        } else {
            System.out.println("Este autorizado ya existe en esta cuenta.");
        }

    }

    public static void eliminarAutorizado(CuentaBancaria cuenta) {
        if (cuenta.getAutorizados().size() > 1) {
            System.out.println("Indique el DNI de la persona que quiere eliminar: ");
            String autorizadoDNI = teclado.nextLine();
            if (cuenta.quitarAutorizado(autorizadoDNI)) {
                System.out.println("La persona autorizada se ha eliminado correctamente.");
                System.out.println("En esta cuenta hay " + (cuenta.getAutorizados().size()) + " personas autorizadas.\n");
            } else {
                System.out.println("No se encuentra ninguna persona autorizada con este DNI");
            }

        } else {
            System.out.println("No puede haber menos de un titular.\n");
        }

    }

    public static String informacionCuenta(CuentaBancaria cuenta) {
        String infoCliente = ("nº de Cuenta: " + cuenta.getNumCuenta() + "\nNombre titular : "
                + cuenta.getTitular() + cuenta.verAutorizados()
                + "\nSaldo : " + cuenta.showSaldo());
        return infoCliente;
    }
}
