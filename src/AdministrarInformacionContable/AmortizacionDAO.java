/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdministrarInformacionContable;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

public class AmortizacionDAO {

    private Connection conn;

    public AmortizacionDAO(Connection conn) {
        this.conn = conn;
    }

    // Verifica si ya existen amortizaciones para una licencia
    public boolean amortizacionesExisten(int idLicencia) {
        String sql = "SELECT 1 FROM amortizacion WHERE idlicencia = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLicencia);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Calcula y guarda amortizaciones (mensuales y anuales)
   public boolean generarAmortizaciones(int idLicencia) {
    if (amortizacionesExisten(idLicencia)) {
        return false; // Ya existen, no generamos
    }

    String sqlLicencia = "SELECT costo, vidautil, fechacompra FROM licencia WHERE idlicencia = ?";
    String sqlInsert = "INSERT INTO amortizacion (idlicencia, tipocartera, monto, fecharegistro, estado) VALUES (?, ?, ?, ?, ?)";


    try (PreparedStatement psLic = conn.prepareStatement(sqlLicencia);
         PreparedStatement psIns = conn.prepareStatement(sqlInsert)) {

        psLic.setInt(1, idLicencia);
        ResultSet rs = psLic.executeQuery();

        if (rs.next()) {
            double costo = rs.getDouble("costo");
            int vidaUtil = rs.getInt("vidautil");
            java.sql.Date fechaCompra = rs.getDate("fechacompra");

            if (vidaUtil <= 0) vidaUtil = 1;

            double amortMensual = costo / (vidaUtil * 12);
            double amortAnual = costo / vidaUtil;

            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaCompra);

            int idCuotaMensual = 1;
            int idCuotaAnual = 1;

            for (int i = 0; i < vidaUtil * 12; i++) {
                cal.add(Calendar.MONTH, 1);
               psIns.setInt(1, idLicencia);
               psIns.setString(2, "mensual");
               psIns.setDouble(3, amortMensual);
               psIns.setTimestamp(4, new java.sql.Timestamp(cal.getTimeInMillis()));
               psIns.setString(5, "pendiente");
               psIns.addBatch();
            }

            cal.setTime(fechaCompra);
            for (int i = 0; i < vidaUtil; i++) {
                cal.add(Calendar.YEAR, 1);
               psIns.setInt(1, idLicencia);
               psIns.setString(2, "anual");
               psIns.setDouble(3, amortAnual);
               psIns.setTimestamp(4, new java.sql.Timestamp(cal.getTimeInMillis()));
               psIns.setString(5, "pendiente");
               psIns.addBatch();
            }

            psIns.executeBatch();
            JOptionPane.showMessageDialog(null, "✅ Amortizaciones generadas correctamente para la licencia.");
            return true;
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al generar amortizaciones: " + e.getMessage());
    }

    return false;
}


    // Listar amortizaciones según el tipo
    public List<Amortizacion> listarAmortizaciones(int idLicencia, String tipo) {
         List<Amortizacion> lista = new ArrayList<>();
        String sql = "";

        switch (tipo.toLowerCase()) {
            case "mensual":
                sql = "SELECT * FROM amortizacion WHERE idlicencia = ? AND tipocartera = 'mensual' ORDER BY fecharegistro ASC";
                break;
            case "anual":
                sql = "SELECT * FROM amortizacion WHERE idlicencia = ? AND tipocartera = 'anual' ORDER BY fecharegistro ASC";
                break;
            case "acumulado":
                sql = "SELECT idlicencia, SUM(monto) AS monto_total FROM amortizacion WHERE idlicencia = ? GROUP BY idlicencia";
                break;
            case "pendiente":
                sql = "SELECT * FROM amortizacion WHERE idlicencia = ? AND estado = 'pendiente'";
                break;
            case "pagada":
                sql = "SELECT * FROM amortizacion WHERE idlicencia = ? AND estado = 'pagada'";
                break;
            default:
                JOptionPane.showMessageDialog(null, "Tipo de amortización no válido.");
                return lista;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idLicencia);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Amortizacion a = new Amortizacion();
                if (tipo.equalsIgnoreCase("acumulado")) {
                    a.setIdLicencia(rs.getInt("idlicencia"));
                    a.setMonto(rs.getDouble("monto_total"));
                    a.setTipoCartera("Acumulado");
                } else {
                    a.setIdAmortizacion(rs.getInt("idamortizacion"));
                    a.setIdLicencia(rs.getInt("idlicencia"));
                    a.setTipoCartera(rs.getString("tipocartera"));
                    a.setMonto(rs.getDouble("monto"));
                    a.setFechaRegistro(rs.getDate("fecharegistro"));
                    a.setEstado(rs.getString("estado"));
                }
                lista.add(a);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar amortizaciones: " + e.getMessage());
        }

        return lista;
    }

    // Cambiar estado (pendiente/pagada)
    public void actualizarEstado(int idAmortizacion, String nuevoEstado) {
       String sql = "UPDATE amortizacion SET estado = ? WHERE idamortizacion = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idAmortizacion);
            ps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar estado: " + e.getMessage());
        }
    }

    public boolean licenciaExiste(int idLicencia) {
         String sql = "SELECT 1 FROM licencia WHERE idlicencia = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLicencia);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
       public void agregarAmortizacion(int idLicencia, String tipo, double monto, java.sql.Timestamp fecha, String estado, int idCuota) {
        String sqlInsert = "INSERT INTO amortizacion (idlicencia, tipocartera, monto, fecharegistro, idcuota, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
            ps.setInt(1, idLicencia);
            ps.setString(2, tipo);
            ps.setDouble(3, monto);
            ps.setTimestamp(4, fecha);
            ps.setInt(5, idCuota);
            ps.setString(6, estado);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "✅ Amortización agregada correctamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar amortización: " + e.getMessage());
        }
    }
}
