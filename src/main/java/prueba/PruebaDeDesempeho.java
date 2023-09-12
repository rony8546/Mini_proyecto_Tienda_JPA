package prueba;

import dao.PedidoDao;
import model.Pedido;
import utils.JPAUtils;

import javax.persistence.EntityManager;
import java.io.FileNotFoundException;

public class PruebaDeDesempeho {

    public static void main(String[] args) throws FileNotFoundException {
        LoadRecords.cargarRegistros();

        EntityManager manager = JPAUtils.getEntityManager();

        PedidoDao pedidoDao = new PedidoDao(manager);
        Pedido pedidoConCliente = pedidoDao.consultarPedidoConCliente(1l);

        manager.close();

//		System.out.println(pedido.getFecha());
//		System.out.println(pedido.getItems().size());
        System.out.println(pedidoConCliente.getCliente().getNombre());

    }
}