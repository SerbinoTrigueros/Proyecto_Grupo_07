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

        JLabel lblValorLibros = new JLabel("Valor en Libros:");
        lblValorLibros.setBounds(50, 280, 120, 25);
        add(lblValorLibros);

        txtValorLibros = new JTextField();
        txtValorLibros.setBounds(170, 280, 150, 25);
        add(txtValorLibros);

        JLabel lblValorPendiente = new JLabel("Valor Pendiente:");
        lblValorPendiente.setBounds(350, 280, 120, 25);
        add(lblValorPendiente);

        txtValorPendiente = new JTextField();
        txtValorPendiente.setBounds(470, 280, 150, 25);
        add(txtValorPendiente);

        btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setBounds(420, 350, 120, 30);
        btnRefrescar.addActionListener(e -> cargarLicencias());
        add(btnRefrescar);
        
        btnMostrarAmortizaciones = new JButton("Mostrar Amortizaciones");
        btnMostrarAmortizaciones.setBounds(50, 350, 200, 30);
        add(btnMostrarAmortizaciones);
           btnMostrarAmortizaciones.addActionListener(e -> {
            FrmMostrarAmortizaciones frm = new FrmMostrarAmortizaciones();
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