/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdministrarInformacionContable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AmortizacionDAO {

    private Connection conn;

    public AmortizacionDAO(Connection conn) {
        this.conn = conn;
    }

    // Método para obtener las amortizaciones según tipo y licencia
    public List<Amortizacion> listarAmortizaciones(int idLicencia, String tipo) {
        List<Amortizacion> lista = new ArrayList<>();
        String sql = "";

        switch (tipo.toLowerCase()) {
            case "mensual":
                sql = "SELECT * FROM amortizacion WHERE idlicencia = ? AND tipocartera = 'mensual' ORDER BY fecharegistro ASC";
                break;
            case "acumulado":
                sql = "SELECT idlicencia, SUM(monto) AS monto_total FROM amortizacion WHERE idlicencia = ? GROUP BY idlicencia";
                break;
            case "pendiente":
                sql = "SELECT * FROM amortizacion WHERE idlicencia = ? AND estado = 'pendiente'";
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
                a.setIdAmortizacion(rs.getInt("idamortizacion"));
                a.setIdLicencia(rs.getInt("idlicencia"));
                a.setTipoCartera(rs.getString("tipocartera"));
                a.setMonto(rs.getDouble("monto"));
                a.setFechaRegistro(rs.getDate("fecharegistro"));
                a.setEstado(rs.getString("estado"));
                lista.add(a);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar amortizaciones: " + e.getMessage());
        }

        return lista;
    }
}