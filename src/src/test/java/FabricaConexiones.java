import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * Created by felipe on 27/08/2016.
 */
public class FabricaConexiones extends BasePooledObjectFactory<Conexion2> {



    public Conexion2 create() throws Exception {
        return new Conexion2();
    }

    public PooledObject<Conexion2> wrap(Conexion2 conexion2) {
        return new DefaultPooledObject<Conexion2>(conexion2);
    }

    @Override
    public void destroyObject(PooledObject<Conexion2> p) throws Exception {
        p.getObject().getConexion().close();
        super.destroyObject(p);
    }
}
