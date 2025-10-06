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
        
        // solo ver id,tipo,costo, la fecha de compra y la fecha fin y la vida util en anios
        modelo.addColumn("ID Licencia"); // Mapea a idlicencia
        modelo.addColumn("Tipo Licencia"); // Mapea a tipolicencia
        modelo.addColumn("Costo"); // Mapea a costo
        modelo.addColumn("Fecha Compra"); // Mapea a fechacompra
        modelo.addColumn("Fecha Fin"); // Mapea a fechafin
        modelo.addColumn("Vida Útil (Años)"); // Mapea a vidautil

        // selecionar las licencias
        String sql = "SELECT idlicencia, tipolicencia, costo, fechacompra, fechafin, vidautil FROM licencia";

        try (Connection conn = ConexionBD.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                //leer solo esas colmunas
                Object[] fila = {
                    rs.getInt("idlicencia"),
                    rs.getString("tipolicencia"),
                    rs.getDouble("costo"),
                    rs.getDate("fechacompra"), 
                    rs.getDate("fechafin"), 
                    rs.getInt("vidautil")
                };
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar licencias: " + e.getMessage());
        }
        return modelo;
    }

    // metodo actualizado
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