/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package activos_intangibles;

import activos_intangibles.Interfaz.Login_screen;
import javax.swing.UIManager;

/**
 *
 * @author serbi
 */
public class Activos_Intangibles {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     
        // Para que se vea con el estilo de tu sistema operativo (opcional)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
           
        }

        // Mostrar el login
        java.awt.EventQueue.invokeLater(() -> {
            new Login_screen().setVisible(true); 
        });
    }
    
}
