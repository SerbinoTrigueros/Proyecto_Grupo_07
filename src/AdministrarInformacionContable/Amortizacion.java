/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdministrarInformacionContable;

import java.util.Date;

public class Amortizacion {

    private int idAmortizacion;
    private String tipoCartera;
    private double monto;
    private Date fechaRegistro;
    private int idLicencia;
    private String estado;

    // 🔹 Constructor vacío (necesario para frameworks o uso general)
    public Amortizacion() {
    }

    // 🔹 Constructor completo (este es el que usa el DAO)
    public Amortizacion(int idAmortizacion, String tipoCartera, double monto, Date fechaRegistro, int idLicencia, String estado) {
        this.idAmortizacion = idAmortizacion;
        this.tipoCartera = tipoCartera;
        this.monto = monto;
        this.fechaRegistro = fechaRegistro;
        this.idLicencia = idLicencia;
        this.estado = estado;
    }

    // 🔹 Getters y Setters
    public int getIdAmortizacion() {
        return idAmortizacion;
    }

    public void setIdAmortizacion(int idAmortizacion) {
        this.idAmortizacion = idAmortizacion;
    }

    public String getTipoCartera() {
        return tipoCartera;
    }

    public void setTipoCartera(String tipoCartera) {
        this.tipoCartera = tipoCartera;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdLicencia() {
        return idLicencia;
    }

    public void setIdLicencia(int idLicencia) {
        this.idLicencia = idLicencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Amortizacion{" +
                "idAmortizacion=" + idAmortizacion +
                ", tipoCartera='" + tipoCartera + '\'' +
                ", monto=" + monto +
                ", fechaRegistro=" + fechaRegistro +
                ", idLicencia=" + idLicencia +
                ", estado='" + estado + '\'' +
                '}';
    }
}
