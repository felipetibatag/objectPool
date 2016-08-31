import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * Created by felipe on 27/08/2016.
 */
public class FabricaControlaLibros extends BasePooledObjectFactory<ControlaLibros> {


    public ControlaLibros create() throws Exception {
        return new ControlaLibros();
    }

    public PooledObject<ControlaLibros> wrap(ControlaLibros controlaLibros) {
        return new DefaultPooledObject<ControlaLibros>(controlaLibros);
    }

    @Override
    public void passivateObject(PooledObject<ControlaLibros> p) throws Exception {
        super.passivateObject(p);
    }
}
