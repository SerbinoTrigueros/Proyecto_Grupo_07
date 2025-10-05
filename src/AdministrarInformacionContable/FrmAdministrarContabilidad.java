/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdministrarInformacionContable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrmAdministrarContabilidad extends JFrame {

    private JTable tablaLicencias;
    private JTextField txtValorLibros, txtValorPendiente;
    private JButton btnActualizar, btnRefrescar;
    private ContabilidadDAO dao;
    private JButton btnMostrarAmortizaciones;
    private JButton btnConsultarValoresEnLibros;

    public FrmAdministrarContabilidad() {
        dao = new ContabilidadDAO();
        initComponents();
        cargarLicencias();
    }

    private void initComponents() {
        setTitle("Administrar Información Contable - SoftControl");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Administración de Información Contable");
        lblTitulo.setBounds(180, 20, 350, 25);
        add(lblTitulo);

        tablaLicencias = new JTable();
        JScrollPane scroll = new JScrollPane(tablaLicencias);
        scroll.setBounds(50, 60, 600, 200);
        add(scroll);

        btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setBounds(490, 280, 120, 25);
        btnRefrescar.addActionListener(e -> cargarLicencias());
        add(btnRefrescar);

        btnMostrarAmortizaciones = new JButton("Mostrar Amortizaciones");
        btnMostrarAmortizaciones.setBounds(50, 280, 200, 25);
        add(btnMostrarAmortizaciones);
        btnMostrarAmortizaciones.addActionListener(e -> {
            FrmMostrarAmortizaciones frm = new FrmMostrarAmortizaciones();
            frm.setVisible(true);
        });
        
        btnConsultarValoresEnLibros = new JButton("Consultar valores en libros");
        btnConsultarValoresEnLibros.setBounds(50, 350, 200, 25);
        add(btnConsultarValoresEnLibros);
        btnConsultarValoresEnLibros.addActionListener(e -> {
            FrmValorLibros frm = new FrmValorLibros();
            frm.setVisible(true);
        });
    }

    private void cargarLicencias() {
        DefaultTableModel modelo = dao.listarLicencias();
        tablaLicencias.setModel(modelo);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmAdministrarContabilidad().setVisible(true));
    }
}
