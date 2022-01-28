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

    // DECLARACION VARIABLES MAIN
    static Scanner teclado = new Scanner(System.in);

    private static final String AVISO_NEGATIVO = "AVISO: Saldo negativo";
    private static final String AVISO_HACIENDA = "AVISO: Notificación a Hacienda";
    private static final String AVISO_FORMATO_DNI = "Este no es un formato correcto de DNI.\n"
            + "Introduzca un DNI con formato correcto, por favor.";
    private static final String AVISO_IMPORTE_INFERIOR = "Introduzca un importe superior a 0.\n";
    private static final String AVISO_CIFRA = "Por favor, introduzca una cifra.\n";
    private static final String MENSAJE_DNI ="Indique su DNI: ";
    private static final String MENSAJE_CONCEPTO = "Indique el concepto para esta transacción: ";
    private static double importe;
    private static String concepto, dni;
    private static boolean datosOK = false;

    public static void main(String[] args) {
        // INICIALIZAMOS UN TITULAR DE LA CLASE PERSONA Y UN CUENTA DE CLASE CUENTA BANCARIA
        Persona titular = new Persona("44888777V", "Santi Miquel");
        CuentaBancaria cuenta = new CuentaBancaria("ES1234567890123456789012", titular);
        boolean bucleMenu = true;
        
        // SWITCH PARA ELECCION DE OPCION POR PARTE DEL USUARIO
        do {
            String respuesta = menu();
            switch (respuesta) {
                case "1" -> System.out.println(informacionCuenta(cuenta));
                case "2" -> hacerIngreso(cuenta);
                case "3" -> hacerRetirada(cuenta);
                case "4" -> registrarAutorizado(cuenta);
                case "5" -> eliminarAutorizado(cuenta);
                case "6" -> System.out.println(cuenta.getMovimientos());
                case "7" -> System.out.println(cuenta.getIngresos());
                case "8" -> System.out.println(cuenta.getRetiradas());
                case "0" -> {
                    bucleMenu = false;
                    System.out.println("Gracias por utilizar nuestros servicios.\n"
                            + "Le esperamos pronto.");
                }
                default -> System.out.println("Introduzca un numero del 0 al 8.\n");

            }
            if (!respuesta.equalsIgnoreCase("0") && !respuesta.equalsIgnoreCase("1")) {
                System.out.println(informacionCuenta(cuenta));
            }
        } while (bucleMenu);

    }
    // METODO PARA MOSTRAR EL MENU
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
        System.out.println("7 - Ver ingresos.");
        System.out.println("8 - Ver retiradas.");
        System.out.println("0 - Salir.");
        respuesta = teclado.nextLine();
        return respuesta;
    }
    
    // METODO PARA HACER INGRESO QUE TRABAJA CON EL METODO INGRESO DE LA CLASE CUENTA BANCARIA
    public static void hacerIngreso(CuentaBancaria cuenta) {
        System.out.println(MENSAJE_DNI);
        do {
            dni = teclado.nextLine();
            if (comprobarDNI()) {
                datosOK = true;
            } else {
                System.out.println(AVISO_FORMATO_DNI);
                datosOK = false;
            }
        } while (!datosOK);
        do {
            try {
                System.out.println("Indique el importe a ingresar: ");
                importe = Double.parseDouble(teclado.nextLine());
                cuenta.ingresar(importe, concepto, dni);

                if (cuenta.getResultadoIngreso() >= 0) {
                    if (cuenta.getResultadoIngreso() > 0) {
                        System.out.println(AVISO_HACIENDA + "\n");
                    }
                    System.out.println(MENSAJE_CONCEPTO);
                    concepto = teclado.nextLine();
                    System.out.println("**Ingreso realizado con éxito**\n");
                    datosOK = true;
                } else {
                    System.out.println(AVISO_IMPORTE_INFERIOR);
                    datosOK = false;
                }
            } catch (NumberFormatException e) {
                System.out.println(AVISO_CIFRA);
                datosOK = false;

            }
        } while (!datosOK);
    }

    // METODO PARA HACER RETIRADA QUE TRABAJA CON EL METODO SACAR DE LA CLASE CUENTA BANCARIA
    public static void hacerRetirada(CuentaBancaria cuenta) {
        System.out.println(MENSAJE_DNI);
        do {
//            teclado = new Scanner(System.in);
            dni = teclado.nextLine();
            if (comprobarDNI()) {
                datosOK = true;
                // comparar dni con los dni q hay almacenados en la clase persona
                if (cuenta.buscarAutorizado(dni)) {
                    do {
                        try {
                            System.out.println("Indique el importe a retirar: ");
                            importe = Double.parseDouble(teclado.nextLine());
                            if (importe > 0) {
                                cuenta.sacar(importe, concepto, dni);
                                if (cuenta.getResultadoRetirada() == -1) {
                                    System.out.println("No se permite tener un saldo por debajo de -50\n");
                                } else {
                                    if (cuenta.getResultadoRetirada() == 0) {
                                        System.out.println(AVISO_NEGATIVO);
                                    }
                                    if (cuenta.getResultadoRetirada() == 2) {
                                        System.out.println(AVISO_HACIENDA);
                                    }
                                    System.out.println(MENSAJE_CONCEPTO);
                                    concepto = teclado.nextLine();
//                                    cuenta.sacar(importe, concepto, dniIngreso);
                                    System.out.println("**Retirada realizada con éxito**\n");
                                    datosOK = true;
                                }
                            } else {
                                System.out.println(AVISO_IMPORTE_INFERIOR);
                                datosOK = false;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(AVISO_CIFRA);
                            datosOK = false;
                        }
                    } while (!datosOK);
                } else {
                    System.out.println("El DNI: " + dni + " no coincide con ningun "
                            + "autorizado en esta cuenta.");
                    System.out.println("**Operacion CANCELADA**\n");
                    System.out.println("Vuelva a introducir la operación deseada en el menu.\n");
                    
                }
            } else {
                System.out.println(AVISO_FORMATO_DNI);
                datosOK = false;
            }
        } while (!datosOK);

    }
    
    // METODO PARA REGISTRAR A UN NUEVO TITULAR QUE TRABAJA CON EL METODO AUTORIZAR DE LA CLASE CUENTA BANCARIA
     public static void registrarAutorizado(CuentaBancaria cuenta) {
        String autorizadoNombre;
        do {
            System.out.println("Indique el DNI del nuevo autorizado: ");
            dni = teclado.nextLine();
            if (comprobarDNI()) {
                System.out.println("Indique el nombre del nuevo autorizado: ");
                datosOK = true;
                autorizadoNombre = teclado.nextLine();
                if (cuenta.autorizar(dni, autorizadoNombre)) {
                    System.out.println("El nuevo autorizado " + cuenta.getRecienAnyadido() + " se ha añadido correctamente");
                    System.out.println("En esta cuenta hay " + (cuenta.getTitulares().size()) + " personas autorizadas.\n");
                } else {
                    System.out.println("Este autorizado ya existe en esta cuenta.");
                }
            } else {
                System.out.println(AVISO_FORMATO_DNI);
                datosOK = false;
            }
        } while (!datosOK);

    }

    // METODO PARA ELIMINAR TITULAR QUE TRABAJA CON EL METODO QUITAR AUTORIZADO DE LA CLASE CUENTA BANCARIA
    public static void eliminarAutorizado(CuentaBancaria cuenta) {
        if (cuenta.getTitulares().size() > 1) {
            System.out.println("Indique el DNI de la persona que quiere eliminar: ");
            dni = teclado.nextLine();
            if (comprobarDNI()) {
                if (cuenta.quitarAutorizado(dni)) {
                    System.out.println("La persona " + cuenta.getRecienEliminado() + " se ha eliminado como titular correctamente.");
                    System.out.println("En esta cuenta hay " + (cuenta.getTitulares().size()) + " personas autorizadas.\n");
                } else {
                    System.out.println("No se encuentra ninguna persona autorizada con este DNI\n");
                }
            } else {
                System.out.println(AVISO_FORMATO_DNI);

            }

        } else {
            System.out.println("No puede haber menos de un titular.\n");
        }

    }
    
    // METODO PARA HACER COMPROBAR QUE EL DNI DADO POR EL USUARIO TIENE UN FORMATO DE DNI CORRECTO
    public static boolean comprobarDNI() {
        boolean formatoCorrecto = false;
        if (dni.matches("^\\d?\\d{7}[-|\\s]?[A-Za-z]$")) {
            formatoCorrecto = true;
        }
        return formatoCorrecto;
    }

    // METODO PARA MOSTRAR LA INFORMACION DE LA CUENTA BANCARIA
    public static String informacionCuenta(CuentaBancaria cuenta) {
        String infoCliente = ("nº de Cuenta: " + cuenta.getNUM_CUENTA() + " - Nombre titular : "
                + cuenta.getTitulares().toString()
                + "\nSaldo : " + cuenta.showSaldo() + "\n");
        return infoCliente;
    }
}
