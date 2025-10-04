/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdministrarInformacionContable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class FrmMostrarAmortizaciones extends JFrame {

    private JComboBox<String> cbTipo;
    private JTextField txtIdLicencia;
    private JTable tabla;
    private AmortizacionDAO dao;

    public FrmMostrarAmortizaciones(Connection conn) {
        dao = new AmortizacionDAO(conn);

        setTitle("Mostrar Amortizaciones");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior
        JPanel panelTop = new JPanel();
        panelTop.add(new JLabel("ID Licencia:"));
        txtIdLicencia = new JTextField(5);
        panelTop.add(txtIdLicencia);

        panelTop.add(new JLabel("Tipo:"));
        cbTipo = new JComboBox<>(new String[]{"mensual", "acumulado", "pendiente"});
        panelTop.add(cbTipo);

        JButton btnMostrar = new JButton("Mostrar");
        panelTop.add(btnMostrar);
        add(panelTop, BorderLayout.NORTH);

        // Tabla
        tabla = new JTable(new DefaultTableModel(
            new Object[]{"ID", "Tipo", "Monto", "Fecha", "Estado"}, 0
        ));
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnMostrar.addActionListener(e -> cargarAmortizaciones());
    }

    private void cargarAmortizaciones() {
        int idLicencia = Integer.parseInt(txtIdLicencia.getText());
        String tipo = (String) cbTipo.getSelectedItem();

        List<Amortizacion> lista = dao.listarAmortizaciones(idLicencia, tipo);
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay amortizaciones registradas para esta licencia.");
        } else {
            for (Amortizacion a : lista) {
                model.addRow(new Object[]{
                    a.getIdAmortizacion(),
                    a.getTipoCartera(),
                    a.getMonto(),
                    a.getFechaRegistro(),
                    a.getEstado()
                });
            }
        }
    }

    public static void main(String[] args) {
        Connection conn = ConexionBD.conectar(); // tu clase de conexión
        SwingUtilities.invokeLater(() -> new FrmMostrarAmortizaciones(conn).setVisible(true));
    }
}