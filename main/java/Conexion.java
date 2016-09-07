import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by felipe on 27/08/2016.
 */
public class Conexion {
    private static Connection cx;


    public Conexion() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        cx= DriverManager.getConnection("jdbc:postgresql://192.168.56.1:5555/swii","swii","swii");
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (cx==null){
            new Conexion();
        }
        return cx;
    }


    public static void cierraConexiones() throws SQLException {
        cx.createStatement().execute("select pg_terminate_backend(pid) from pg_stat_activity where pid<> pg_backend_pid() and datname ='swii'");
    }

}
