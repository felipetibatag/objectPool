import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.postgresql.util.PSQLException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by felipe on 27/08/2016.
 */
public class Probador {

    ObjectPool<ControlaLibros> pool;
    ControlaLibros controlaLibros;
    GenericObjectPoolConfig config;

    @BeforeTest
    public void configura() throws Exception {
        config=new GenericObjectPoolConfig();
        config.setMaxTotal(1);
        pool=new GenericObjectPool<ControlaLibros>(new FabricaControlaLibros(),config);
    }

    @Test
    public void debeCrearSoloUnaConexionAlaBaseDeDatos() throws SQLException, ClassNotFoundException {
        Connection c1= Conexion.getConnection();
        Connection c2= Conexion.getConnection();
        Assert.assertEquals(c1,c2);
    }

    @Test(invocationCount = 100,dependsOnMethods = "debeCrearSoloUnaConexionAlaBaseDeDatos")
    public void inserta10LibrosConExito() throws Exception {
        controlaLibros=pool.borrowObject();
        controlaLibros.setLibro(new Libro("Titulo ","Autor","Editorial"));
        pool.returnObject(controlaLibros);
        System.out.println(pool.getNumActive());
        Assert.assertEquals(controlaLibros.guardar(),1);

    }

    @Test(dependsOnMethods = "inserta10LibrosConExito")
    public void modificaUnRegistroConExitoDadosTodosSusCampos() throws Exception {
        controlaLibros=pool.borrowObject();
        Libro libro=new Libro();
        libro.setCodigo(11272);
        libro.setEditorial("XXXX");
        libro.setAutor("AAAAA");
        libro.setTitulo("AAAA");
        controlaLibros.setLibro(libro);
        pool.returnObject(controlaLibros);
        Assert.assertEquals(controlaLibros.editar(),1);
    }
    @Test(dependsOnMethods = "modificaUnRegistroConExitoDadosTodosSusCampos")
    public void borraUnLibroConExito() throws Exception {
        controlaLibros=pool.borrowObject();
        controlaLibros.setLibro(new Libro(11272));
        pool.returnObject(controlaLibros);
        Assert.assertEquals(controlaLibros.eliminar(),1);
    }

    @Test (dependsOnMethods = "borraUnLibroConExito")
    public void recibeUnCeroAlNoEncontrarUnLibroEnviadoParaModificar() throws Exception {
        controlaLibros=pool.borrowObject();
        Libro libro=new Libro();
        libro.setCodigo(8);
        libro.setEditorial("XXXX");
        libro.setAutor("AAAAA");
        libro.setTitulo("AAAA");
        controlaLibros.setLibro(libro);
        pool.returnObject(controlaLibros);
        Assert.assertEquals(controlaLibros.editar(),0);
    }


    @Test(dependsOnMethods = "recibeUnCeroAlNoEncontrarUnLibroEnviadoParaModificar")
    public void recibeunCeroAlNoPoderEliminarUnLibro() throws Exception {
        controlaLibros=pool.borrowObject();
        controlaLibros.setLibro(new Libro(1));
        pool.returnObject(controlaLibros);
        Assert.assertEquals(controlaLibros.eliminar(),0);
    }


    @Test(expectedExceptions = PSQLException.class,dependsOnMethods = "recibeunCeroAlNoPoderEliminarUnLibro")
    public void recibeExcepcionAlNoTenerConexionConLaBDalLlegarAlLimite() throws SQLException, ClassNotFoundException {
        for (int i = 0; i <6 ; i++) {
            Connection cx= DriverManager.getConnection("jdbc:postgresql://192.168.56.1:5555/swii","swii","swii");
        }
        //Conexion.cierraConexiones();

    }

}
