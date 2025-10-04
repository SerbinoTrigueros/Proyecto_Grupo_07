/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdministrarInformacionContable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:postgresql://localhost:5433/ss23018";
    private static final String USER = "ss23018";
    private static final String PASSWORD = "ss23018";

    public static Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(" Conexión exitosa");
        } catch (SQLException e) {
            System.err.println("Ops ocurrio un error al conectar con la base de datos: " + e.getMessage());
        }
        return conn;
    }
}
