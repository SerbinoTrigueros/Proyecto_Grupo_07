/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdministrarInformacionContable;

import java.util.Date;

public class Amortizacion {
    private int idAmortizacion;
    private int idLicencia;
    private String tipoCartera;
    private double monto;
    private Date fechaRegistro;
    private String estado;

    // Getters y Setters
    public int getIdAmortizacion() { return idAmortizacion; }
    public void setIdAmortizacion(int idAmortizacion) { this.idAmortizacion = idAmortizacion; }

    public int getIdLicencia() { return idLicencia; }
    public void setIdLicencia(int idLicencia) { this.idLicencia = idLicencia; }

    public String getTipoCartera() { return tipoCartera; }
    public void setTipoCartera(String tipoCartera) { this.tipoCartera = tipoCartera; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}