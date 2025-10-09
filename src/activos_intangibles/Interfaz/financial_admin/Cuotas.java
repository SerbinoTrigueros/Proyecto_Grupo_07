/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package activos_intangibles.Interfaz.financial_admin;

/**
 *
 * @author cliente
 */
public class Cuotas {
    private int idCuota;
    private int numeroCuota;
    private double monto;
    private String estado;
    private int idLicencia;

    public Cuotas() {
    }

    public Cuotas(int idCuota, int numeroCuota, double monto, String estado, int idLicencia) {
        this.idCuota = idCuota;
        this.numeroCuota = numeroCuota;
        this.monto = monto;
        this.estado = estado;
        this.idLicencia = idLicencia;
    }

    public int getIdCuota() {
        return idCuota;
    }

    public void setIdCuota(int idCuota) {
        if(idCuota > 0)
            this.idCuota = idCuota;
    }

    public int getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(int numeroCuota) {
        if(numeroCuota > 0)
            this.numeroCuota = numeroCuota;

    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        if(monto > 0)
            this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        if(!estado.isEmpty())
            this.estado = estado;
    }

    public int getIdLicencia() {
        return idLicencia;
    }

    public void setIdLicencia(int idLicencia) {
        if(idLicencia > 0)
            this.idLicencia = idLicencia;
    }
    
    
}
