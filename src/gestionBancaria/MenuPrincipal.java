/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionBancaria;

import java.util.Scanner;

/**
 *
 * @author santimiquel
 */
public class MenuPrincipal {

    static Scanner teclado = new Scanner(System.in);
    static Scanner sc = new Scanner(System.in); //creo un segundo scanner para que no se me duplique el menu al ingresar o sacar efectivo

    public static void main(String[] args) {
        Persona titular = new Persona("44874926V", "Santi Miquel");
        CuentaBancaria cuenta1 = new CuentaBancaria("ES1234567890123456789012", titular);
        
        boolean bucleMenu = true;
        System.out.println(cuenta1.verAutorizados());
        do {
            String respuesta = menu();
            switch (respuesta) {
                case "1":
                    System.out.println(cuenta1.informacionCuenta());
                    break;
                case "2":
                    System.out.println("Indique el importe a ingresar: ");
                    cuenta1.ingresar(teclado.nextDouble());
                    break;
                case "3":
                    System.out.println("Indique el importe a retirar: ");
                    cuenta1.sacar(teclado.nextDouble());
                    break;
                case "4":
                    cuenta1.autorizar();
                    break;
                case "5":
                    cuenta1.quitarAutorizado();
                    break;
                case "6":
                    System.out.println(cuenta1.getMovimientos());
                    break;
                case "0":
                    bucleMenu = false;
                    System.out.println("Gracias por utilizar nuestros servicios.\n"
                            + "Le esperamos pronto.");
                    break;
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
}
