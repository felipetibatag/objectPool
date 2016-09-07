import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by felipe on 06/09/2016.
 */
public class Conexion2 {
    private static String servidor="jdbc:postgresql://192.168.56.1:5555/swii";
    private static String usuario="swii";
    private static String clave="swii";
    private static String driver="org.postgresql.Driver";
    private static Connection conexion;

    public Conexion2() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        conexion= DriverManager.getConnection(servidor,usuario,clave);
    }
    public Connection getConexion(){
        return conexion;
    }
}
