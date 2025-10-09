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
    private JComboBox<String> cbEstado;
    private JTextField txtIdLicencia;
    private JTable tabla;
    private AmortizacionDAO dao;

    public FrmMostrarAmortizaciones(Connection conn) {
        dao = new AmortizacionDAO(conn);

        setTitle("Mostrar Amortizaciones");
        setSize(850, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        panelTop.add(new JLabel("ID Licencia:"));
        txtIdLicencia = new JTextField(5);
        panelTop.add(txtIdLicencia);

        // Selección de tipo (mensual / anual / acumulado)
        panelTop.add(new JLabel("Tipo:"));
        cbTipo = new JComboBox<>(new String[]{"mensual", "anual", "acumulado"});
        panelTop.add(cbTipo);

        // Selección de estado (pendiente / pagada / todos)
        panelTop.add(new JLabel("Estado:"));
        cbEstado = new JComboBox<>(new String[]{"todos", "pendiente", "pagada"});
        panelTop.add(cbEstado);

        // Botones
        JButton btnMostrar = new JButton("Mostrar");
        JButton btnEstado = new JButton("Marcar como Pagada");
        panelTop.add(btnMostrar);
        panelTop.add(btnEstado);

        add(panelTop, BorderLayout.NORTH);

        // Tabla
        tabla = new JTable(new DefaultTableModel(
                new Object[]{"ID", "Tipo", "Monto", "Fecha", "Estado"}, 0
        ));
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Eventos
        btnMostrar.addActionListener(e -> cargarAmortizaciones());
        btnEstado.addActionListener(e -> cambiarEstado());
    }

    public FrmMostrarAmortizaciones() {
        this(ConexionBD.conectar());
    }

    private void cargarAmortizaciones() {
         try {
            int idLicencia = Integer.parseInt(txtIdLicencia.getText());

            if (!dao.licenciaExiste(idLicencia)) {
                JOptionPane.showMessageDialog(this, "La licencia con ID " + idLicencia + " no existe.");
                return;
            }

            // ✅ Generar automáticamente las amortizaciones si no existen
            dao.generarAmortizaciones(idLicencia);

            String tipo = (String) cbTipo.getSelectedItem();
            String estado = (String) cbEstado.getSelectedItem();

            List<Amortizacion> lista = dao.listarAmortizaciones(idLicencia, tipo);
            DefaultTableModel model = (DefaultTableModel) tabla.getModel();
            model.setRowCount(0);

            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay amortizaciones registradas para esta licencia.");
            } else {
                if (tipo.equals("acumulado")) {
                    model.setColumnIdentifiers(new Object[]{"ID Licencia", "Tipo", "Monto Total", "", ""});
                } else {
                    model.setColumnIdentifiers(new Object[]{"ID", "Tipo", "Monto", "Fecha", "Estado"});
                }

                for (Amortizacion a : lista) {
                    // Filtrar por estado si el usuario selecciona algo específico
                    if (!estado.equals("todos") && !a.getEstado().equalsIgnoreCase(estado)) {
                        continue;
                    }

                    if (tipo.equals("acumulado")) {
                        model.addRow(new Object[]{
                                a.getIdLicencia(),
                                a.getTipoCartera(),
                                a.getMonto(),
                                "",
                                ""
                        });
                    } else {
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

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido para el ID de licencia.");
        }
    }

    private void cambiarEstado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una amortización de la tabla.");
            return;
        }

         int idAmortizacion = (int) tabla.getValueAt(fila, 0);
        String nuevoEstado = "pagada";

        dao.actualizarEstado(idAmortizacion, nuevoEstado);
        JOptionPane.showMessageDialog(this, "Amortización marcada como pagada.");
        cargarAmortizaciones();
    }

    public static void main(String[] args) {
        Connection conn = ConexionBD.conectar();
        SwingUtilities.invokeLater(() -> new FrmMostrarAmortizaciones(conn).setVisible(true));
    }
}
