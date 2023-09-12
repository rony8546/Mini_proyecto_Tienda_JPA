package prueba;

import dao.CategoriaDao;
import dao.ProductoDao;
import model.Categoria;
import model.Producto;
import utils.JPAUtils;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PruebaAPICriteria {

    public static void main(String[] args) {
        cargarBancoDeDatos();


        EntityManager em = JPAUtils.getEntityManager();
        ProductoDao productoDao =new ProductoDao(em);

        List<Producto> resultado = productoDao.consultarPorParametrosConAPICriteria("X", null, null);

        System.out.println(resultado.get(0).getDescripcion());
    }
    private static void cargarBancoDeDatos() {
        Categoria celulares = new Categoria("CELULARES");
        Categoria videoJuegos = new Categoria("VIDEO_JUEGOS");
        Categoria electronicos = new Categoria("ELECTRONICOS");

        Producto celular = new Producto("X","producto nuevo",new BigDecimal(10000),celulares);
        Producto videoJuego = new Producto("FIFA","2000",new BigDecimal(10000),videoJuegos);
        Producto memoria = new Producto("memoria ram","30 GB",new BigDecimal(10000),electronicos);

        EntityManager manager = JPAUtils.getEntityManager();
        ProductoDao productoDao = new ProductoDao(manager);
        CategoriaDao categoriaDao = new CategoriaDao(manager);

        manager.getTransaction().begin();

        categoriaDao.guardar(celulares);
        categoriaDao.guardar(videoJuegos);
        categoriaDao.guardar(electronicos);

        productoDao.guardar(celular);
        productoDao.guardar(videoJuego);
        productoDao.guardar(memoria);

        manager.getTransaction().commit();
        manager.close();
    }
}
