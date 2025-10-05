/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdministrarInformacionContable;

/**
 *
 * @author Dell
 */
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class FrmValorLibros extends JFrame {

    private JTextField txtIdLicencia;
    private JTextField txtCosto;
    private JTextField txtAcumulado;
    private JTextField txtValorLibros;
    private ValorLibrosDAO dao;

    public FrmValorLibros(Connection conn) {
        dao = new ValorLibrosDAO(conn);

        setTitle("Consultar Valor en Libros");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
        setLayout(new BorderLayout(10, 10));

        // Panel Principal para campos
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. ID Licencia
        panelForm.add(new JLabel("ID Licencia:"));
        txtIdLicencia = new JTextField(5);
        panelForm.add(txtIdLicencia);

        // 2. Botón Consultar (va aquí para mejor flujo visual)
        JButton btnConsultar = new JButton("Consultar");
        panelForm.add(new JLabel("")); // Espacio vacío para la rejilla
        panelForm.add(btnConsultar);
        
        // 3. Costo de Adquisición (Salida)
        panelForm.add(new JLabel("Costo de Adquisición:"));
        txtCosto = new JTextField();
        txtCosto.setEditable(false);
        panelForm.add(txtCosto);

        // 4. Amortizaciones Acumuladas (Salida)
        panelForm.add(new JLabel("Amortizaciones Acumuladas:"));
        txtAcumulado = new JTextField();
        txtAcumulado.setEditable(false);
        panelForm.add(txtAcumulado);

        // 5. Valor en Libros (Resultado Final)
        panelForm.add(new JLabel("VALOR EN LIBROS:"));
        txtValorLibros = new JTextField();
        txtValorLibros.setEditable(false);
        txtValorLibros.setFont(new Font("Arial", Font.BOLD, 14)); // Destacar el resultado
        panelForm.add(txtValorLibros);

        add(panelForm, BorderLayout.CENTER);

        // Eventos
        btnConsultar.addActionListener(e -> consultarValorLibros());
    }

    // Constructor para la ejecución
    public FrmValorLibros() {
        // Asumiendo que ConexionBD.conectar() es accesible
        this(ConexionBD.conectar()); 
    }

    private void consultarValorLibros() {
        // Limpiar campos anteriores
        txtCosto.setText("");
        txtAcumulado.setText("");
        txtValorLibros.setText("");
        
        try {
            int idLicencia = Integer.parseInt(txtIdLicencia.getText());
            ValorLibros vl = dao.calcularValorLibros(idLicencia);

            if (vl == null) {
                JOptionPane.showMessageDialog(this, "La licencia con ID " + idLicencia + " no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Formato de moneda simple (puedes ajustarlo)
            java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0.00");
            
            double costo = vl.getCostoAdquisicion();
            double acumulado = vl.getAmortizacionesAcumuladas();
            double valorLibros = vl.getValorEnLibros();

            // Rellenar campos
            txtCosto.setText(df.format(costo));
            txtAcumulado.setText(df.format(acumulado));
            txtValorLibros.setText(df.format(valorLibros));

            // Flujo Alternativo 14.2.1: Licencia sin amortizaciones
            if (acumulado == 0) {
                JOptionPane.showMessageDialog(this, "La licencia no tiene amortizaciones registradas. Valor en libros = Costo.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID de licencia válido (número entero).", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Asegúrate de que la conexión funcione antes de mostrar la ventana
        SwingUtilities.invokeLater(() -> new FrmValorLibros().setVisible(true));
    }
}