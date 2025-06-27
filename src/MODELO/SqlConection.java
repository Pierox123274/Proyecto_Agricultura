
package MODELO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConection {
     private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=agricultura;encrypt=false";
    private static final String USER = "sa";
    private static final String PASS = "123456";

    public static Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar con la base de datos: " + e.getMessage());
        }
        return conn;
    }
    
}
