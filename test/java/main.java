import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * Created by felipe on 27/08/2016.
 */
public class main {
    public static void main(String[] args) throws Exception {
        ObjectPool<ControlaLibros> pool=new GenericObjectPool<ControlaLibros>(new FabricaControlaLibros());
        ControlaLibros controlaLibros=pool.borrowObject();

        controlaLibros.setLibro(new Libro("Blanca Nieves","Pepinito","Sutanito"));


        controlaLibros.guardar();

        for (Libro libro:controlaLibros.consultaTodos()) {
            System.out.println(libro.getCodigo() + " " + libro.getAutor());
        }
        // falta devolver el objeto al pool!!

    }
}
