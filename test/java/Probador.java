import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Connection;

/**
 * Created by felipe on 27/08/2016.
 */
public class Probador {

    //ObjectPool<conexion2> pool;
    Connection connection;
    Conexion2 conexion2;
    DaoLibro daoLibro;
    ControlaLibros controlaLibros;
    GenericObjectPoolConfig config;
    GenericObjectPool<Conexion2> pool;
    FabricaConexiones fabricaConexiones;

    @BeforeTest
    public void configura() throws Exception {
        config=new GenericObjectPoolConfig();
        config.setMaxTotal(5);
        config.setBlockWhenExhausted(true);
        config.setMaxWaitMillis(30*1000);
        fabricaConexiones=new FabricaConexiones();
        pool= new GenericObjectPool<Conexion2>(fabricaConexiones,config);

    }

    @Test
    public void debeCrearSoloUnaConexionAlaBaseDeDatos() throws Exception {
        /*Connection c1= Conexion.getConnection();
        Connection c2= Conexion.getConnection();*/
        Connection c1=pool.borrowObject().getConexion();
        //Assert.assertEquals(c1,c2);
    }

    @Test(invocationCount = 100,dependsOnMethods = "debeCrearSoloUnaConexionAlaBaseDeDatos")
    public void inserta10LibrosConExito() throws Exception {
        Connection connection=pool.borrowObject().getConexion();
        daoLibro=new DaoLibro(connection);
        int actual=daoLibro.guardar(new Libro("Titulo ","Autor","Editorial"));
        //conexion2.setLibro(new Libro("Titulo ","Autor","Editorial"));
        pool.returnObject(conexion2);
        Assert.assertEquals(actual,1);

    }



}
