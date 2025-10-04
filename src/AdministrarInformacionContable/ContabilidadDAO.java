/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdministrarInformacionContable;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ContabilidadDAO {

    public DefaultTableModel listarLicencias() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Tipo Licencia");
        modelo.addColumn("Costo");
        modelo.addColumn("Valor en Libros");
        modelo.addColumn("Valor Pendiente");

        try (Connection conn = ConexionBD.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT idlicencia, tipolicencia, costo, valorenlibros, valorpendientes FROM licencia")) {

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("idlicencia"),
                    rs.getString("tipolicencia"),
                    rs.getDouble("costo"),
                    rs.getDouble("valorenlibros"),
                    rs.getDouble("valorpendientes")
                };
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar licencias: " + e.getMessage());
        }
        return modelo;
    }

    public boolean actualizarLicencia(int idLicencia, double valorLibros, double valorPendiente) {
        String sql = "UPDATE licencia SET valorenlibros = ?, valorpendientes = ? WHERE idlicencia = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, valorLibros);
            ps.setDouble(2, valorPendiente);
            ps.setInt(3, idLicencia);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar licencia: " + e.getMessage());
            return false;
        }
    }
}