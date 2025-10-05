/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdministrarInformacionContable;

/**
 *
 * @author Dell
 */
import java.sql.*;
import javax.swing.JOptionPane;

public class ValorLibrosDAO {

    private Connection conn;
    private AmortizacionDAO amortizacionDAO;

    public ValorLibrosDAO(Connection conn) {
        this.conn = conn;
        this.amortizacionDAO = new AmortizacionDAO(conn); // Reutilizamos el DAO existente
    }

    /**
     * Calcula y retorna el Valor en Libros para una licencia.
     * @param idLicencia ID de la licencia a consultar.
     * @return Objeto ValorLibros con los datos o null si la licencia no existe.
     */
    public ValorLibros calcularValorLibros(int idLicencia) {
        // 1. Verificar si la licencia existe (Se puede usar el método de AmortizacionDAO)
        if (!amortizacionDAO.licenciaExiste(idLicencia)) {
            return null;
        }
        
        // 2. Obtener Costo de Adquisición
        String sqlCosto = "SELECT costo FROM licencia WHERE idlicencia = ?";
        double costo = 0.0;
        
        try (PreparedStatement psCosto = conn.prepareStatement(sqlCosto)) {
            psCosto.setInt(1, idLicencia);
            ResultSet rs = psCosto.executeQuery();
            if (rs.next()) {
                costo = rs.getDouble("costo");
            } else {
                // Esto no debería pasar si la verificación inicial fue exitosa, pero es un seguro
                return null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el costo de la licencia: " + e.getMessage());
            return null;
        }

        // 3. Obtener Amortizaciones Acumuladas
        // Usamos la misma consulta de SUM(monto) que ya tienes definida.
        String sqlAcumulado = "SELECT SUM(monto) AS monto_total FROM amortizacion WHERE idlicencia = ?";
        double acumulado = 0.0;

        try (PreparedStatement psAcumulado = conn.prepareStatement(sqlAcumulado)) {
            psAcumulado.setInt(1, idLicencia);
            ResultSet rs = psAcumulado.executeQuery();
            if (rs.next()) {
                // SUM() retorna 0.0 o NULL si no hay registros, lo cual se maneja bien
                acumulado = rs.getDouble("monto_total");
            }
        } catch (SQLException e) {
            // Este error puede indicar que la tabla 'amortizacion' no existe o está mal.
            JOptionPane.showMessageDialog(null, "Error al obtener amortizaciones acumuladas: " + e.getMessage());
            return null;
        }
        
        // 4. Calcular el Valor en Libros
        double valorLibros = costo - acumulado;
        
        // 5. Crear y poblar el objeto de resultado
        ValorLibros vl = new ValorLibros();
        vl.setIdLicencia(idLicencia);
        vl.setCostoAdquisicion(costo);
        vl.setAmortizacionesAcumuladas(acumulado);
        vl.setValorEnLibros(valorLibros);
        
        return vl;
    }
}