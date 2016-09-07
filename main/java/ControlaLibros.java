import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by felipe on 27/08/2016.
 */
public class ControlaLibros {
    private DaoLibro daoLibro;
    private Libro libro;

    public ControlaLibros() throws Exception {
        daoLibro=new DaoLibro();
        libro=new Libro();
    }

    public ControlaLibros(DaoLibro daoLibro) {
        this.daoLibro = daoLibro;
        libro=new Libro();
    }

    public int guardar() throws SQLException {
        return daoLibro.guardar(libro);
    }
    public int eliminar() throws SQLException {
        return daoLibro.eliminar(libro);
    }
    public int editar() throws SQLException {
        return daoLibro.editar(libro);
    }

    public DaoLibro getDaoLibro() {
        return daoLibro;
    }

    public void setDaoLibro(DaoLibro daoLibro) {
        this.daoLibro = daoLibro;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }
    public ArrayList<Libro> consultaTodos() throws SQLException {
        return daoLibro.leerTodos();
    }


}
