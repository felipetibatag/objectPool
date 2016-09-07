import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by felipe on 28/08/2016.
 */
public class DaoLibro {
    private PreparedStatement preparedStatement;
    private Connection connection;
    //private Conexion2 conexion2;
    //private GenericObjectPool<Conexion2>pool;
    public DaoLibro() throws Exception {
        //connection=Conexion.getConnection();
        /*conexion2=null;
        conexion2=pool.borrowObject();
        connection=conexion2.getConexion();
        if(conexion2!=null){
            pool.returnObject(conexion2);
        }
*/
    }

    public DaoLibro(Connection connection) {
        this.connection = connection;
    }

    public int guardar(Libro libro) throws SQLException {
        preparedStatement=null;
        int resultado=0;
        preparedStatement=connection.prepareStatement("INSERT  INTO  libros(titulo, autor, editorial) VALUES (?,?,?)");
        preparedStatement.setString(1,libro.getTitulo());
        preparedStatement.setString(2,libro.getAutor());
        preparedStatement.setString(3,libro.getEditorial());
        resultado=preparedStatement.executeUpdate();
        preparedStatement.close();
        return resultado;
    }

    public int eliminar(Libro libro) throws SQLException {
        preparedStatement=null;
        int resultado=0;
        preparedStatement=connection.prepareStatement("DELETE FROM libros WHERE codigo=?");
        preparedStatement.setInt(1,libro.getCodigo());
        resultado=preparedStatement.executeUpdate();
        return resultado;
    }

    public int editar(Libro libro) throws SQLException {
        preparedStatement=null;
        int resultado=0;
        preparedStatement=connection.prepareStatement("UPDATE libros set titulo=?,  autor=?,editorial=? where codigo=?");
        preparedStatement.setString(1,libro.getTitulo());
        preparedStatement.setString(2,libro.getAutor());
        preparedStatement.setString(3,libro.getEditorial());
        preparedStatement.setInt(4,libro.getCodigo());
        resultado=preparedStatement.executeUpdate();
        preparedStatement.close();
        return  resultado;

    }
    public ArrayList<Libro> leerTodos() throws SQLException {
        preparedStatement=null;
        ResultSet resultSet=null;
        ArrayList<Libro> libroArrayList=new ArrayList<Libro>();
        Libro libro=null;
        preparedStatement= connection.prepareStatement("SELECT * FROM libros");
        resultSet=preparedStatement.executeQuery();
        while (resultSet.next()){
            libro= new Libro();
            libro.setCodigo(resultSet.getInt("codigo"));
            libro.setTitulo(resultSet.getString("titulo"));
            libro.setAutor(resultSet.getString("autor"));
            libro.setEditorial(resultSet.getString("editorial"));
            libroArrayList.add(libro);
        }
        resultSet.close();
        preparedStatement.close();
        return libroArrayList;

    }
}
