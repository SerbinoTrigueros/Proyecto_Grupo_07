/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdministrarInformacionContable;

/**
 *
 * @author Dell
 */
// package AdministrarInformacionContable;

public class ValorLibros {
    private int idLicencia;
    private double costoAdquisicion;
    private double amortizacionesAcumuladas;
    private double valorEnLibros;

    // Constructor vacío
    public ValorLibros() {}

    // Getters y Setters

    public int getIdLicencia() {
        return idLicencia;
    }

    public void setIdLicencia(int idLicencia) {
        this.idLicencia = idLicencia;
    }

    public double getCostoAdquisicion() {
        return costoAdquisicion;
    }

    public void setCostoAdquisicion(double costoAdquisicion) {
        this.costoAdquisicion = costoAdquisicion;
    }

    public double getAmortizacionesAcumuladas() {
        return amortizacionesAcumuladas;
    }

    public void setAmortizacionesAcumuladas(double amortizacionesAcumuladas) {
        this.amortizacionesAcumuladas = amortizacionesAcumuladas;
    }

    public double getValorEnLibros() {
        return valorEnLibros;
    }

    public void setValorEnLibros(double valorEnLibros) {
        this.valorEnLibros = valorEnLibros;
    }
}