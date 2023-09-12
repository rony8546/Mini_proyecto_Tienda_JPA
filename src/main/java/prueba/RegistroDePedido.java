package prueba;

import java.io.FileNotFoundException;


import javax.persistence.EntityManager;


import dao.ClienteDao;
import dao.PedidoDao;
import dao.ProductoDao;
import model.Cliente;
import model.ItemsPedido;
import model.Pedido;
import model.Producto;
import utils.JPAUtils;

public class RegistroDePedido {
    public static void main(String[] args) throws FileNotFoundException {
        registrarProducto();
        LoadRecords.cargarRegistros();

        EntityManager manager = JPAUtils.getEntityManager();

        ProductoDao productoDao = new ProductoDao(manager);
        Producto producto = productoDao.buscarPorId(1L);

        ClienteDao clienteDao = new ClienteDao(manager);
        PedidoDao pedidoDao = new PedidoDao(manager);

        Cliente cliente = new Cliente("Juan","k6757kjb");
        Pedido pedido = new Pedido(cliente);
        if (producto != null) {
            pedido.agregarItems(new ItemsPedido(5, producto, pedido));
        } else {
                System.err.println("El objeto producto es null.");
            }
        manager.getTransaction().begin();

        clienteDao.guardar(cliente);
        pedidoDao.guardar(pedido);

        manager.getTransaction().commit();



    }

    private static void registrarProducto() {
		/*Categoria celulares = new Categoria("CELULARES");

		Producto celular = new Producto("Xiaomi Redmi", "Muy bueno", new BigDecimal("800"), celulares);

	    EntityManager em = JPAUtils.getEntityManager();
	    ProductoDao productoDao = new ProductoDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);

	    em.getTransaction().begin();

	    categoriaDao.guardar(celulares);
	    productoDao.guardar(celular);

	    em.getTransaction().commit();
	    em.close();*/
    }
}
