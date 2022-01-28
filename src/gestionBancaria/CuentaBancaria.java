/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionBancaria;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author santimiquel
 */
public class CuentaBancaria {

    // ATRIBUTOS CLASE CUENTA BANCARIA
    private final String NUM_CUENTA;
    private Persona titular;
    private double saldo;
    private DecimalFormat formateador = new DecimalFormat("###,###.##€");

    private List<Movimiento> movimientos;
    private Set<Persona> titulares = new HashSet<>();

    private String recienEliminado, recienAnyadido;
    private Persona buscarAutorizado;

    private final int MOVIMIENTO_GRANDE = 3000;
    private final int SALDO_MINIMO = -50;
    private int resultadoIngreso, resultadoRetirada;

    // CONSTRUCTOR
    public CuentaBancaria(String ncuenta, Persona titular) {
        this.NUM_CUENTA = ncuenta;
        this.titular = titular;
        titulares.add(titular);
        saldo = 0;
        movimientos = new ArrayList<>();
    }

    // GETTERS Y SETTERS
    public String getNUM_CUENTA() {
        return NUM_CUENTA;
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

    public int getResultadoIngreso() {
        return resultadoIngreso;
    }

    public int getResultadoRetirada() {
        return resultadoRetirada;
    }

    // METODO AUTORIZAR NUEVO TITULAR
    public boolean autorizar(String autorizadoDNI, String autorizadoNombre) {
        Persona autorizado = new Persona(autorizadoDNI, autorizadoNombre);
        boolean autorizadoAnyadido = false;
        if (buscarAutorizado(autorizadoDNI)) {
            autorizadoAnyadido = false;
        } else {
            autorizadoAnyadido = titulares.add(autorizado);
            recienAnyadido = autorizado.toString();
            autorizadoAnyadido = true;

        }
        return autorizadoAnyadido;

    }

    // METODO ELIMINAR TITULAR
    public boolean quitarAutorizado(String autorizadoDNI) {
        boolean autorizadoEliminado = false;
        if (buscarAutorizado(autorizadoDNI)) {
            autorizadoEliminado = titulares.remove(buscarAutorizado);
            recienEliminado = buscarAutorizado.toString();
        }

        return autorizadoEliminado;

    }

    // METODO BUSCAR SI EL TITULAR YA EXISTE EN EL VECTOR DE TITULARES
    public boolean buscarAutorizado(String autorizadoDNI) {
        boolean autorizadoExiste = false;
        for (Persona buscarTitular : titulares) {
            if (buscarTitular.igual(autorizadoDNI)) {
                buscarAutorizado = buscarTitular;
                autorizadoExiste = true;
                break;
            }
        }
        return autorizadoExiste;
    }

    // METODO PARA AÑADIR NUEVO MOVIMIENTO AL VECTOR DE MOVIMIENTOS
    private boolean registrarMovimiento(double importe, String concepto, String dniIngreso) {
        Movimiento mov = new Movimiento(importe, concepto, dniIngreso);
        return movimientos.add(mov);
    }

    // METODO PARA REALIZAR INGRESO
    public int ingresar(double importe, String concepto, String dniIngreso) {
        if (importe > 0) {
            if (importe >= MOVIMIENTO_GRANDE) {
                resultadoIngreso = 1;

            } else {
                resultadoIngreso = 0;

            }
            saldo += importe;
            registrarMovimiento(importe, concepto, dniIngreso);
        } else {
            resultadoIngreso = -1;
        }
        return resultadoIngreso;
    }

    // METODO PARA REALIZAR RETIRADA
    public int sacar(double importe, String concepto, String dniIngreso) {
        if (importe > 0) {
            if ((saldo - importe) < SALDO_MINIMO) {
                resultadoRetirada = -1;
            } else {
                saldo -= importe;
                if (saldo < 0) {
                    resultadoRetirada = 0;
                } else {
                    resultadoRetirada = 1;
                }
                if (importe >= MOVIMIENTO_GRANDE) {
                    resultadoRetirada = 2;
                }
                registrarMovimiento((importe * -1), concepto, dniIngreso);
            }
        }
        return resultadoRetirada;
    }
       
    // METODO PARA OBTENER TODOS LOS MOVIMIENTOS
    public String getMovimientos() {
        String movimientoList = "";
        for (Movimiento movimiento : movimientos) {
            movimientoList += movimiento.toString() + "\n";
        }
        return movimientoList;
    }
    
    // METODO PARA OBTENER LOS MOVIMIENTOS DE INGRESO
    public String getIngresos() {
        String movPositivos = "";
        for (int i = 0; i < movimientos.size(); i++) {
            if (movimientos.get(i).getImporte() > 0) {
                movPositivos += movimientos.get(i).toString() + "\n";
            }
        }
        return movPositivos;
    }

    // METODO PARA OBTENER LOS MOVIMIENTOS DE RETIRADA
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
