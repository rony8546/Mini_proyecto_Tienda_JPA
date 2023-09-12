package prueba;

import dao.CategoriaDao;
import dao.ClienteDao;
import dao.PedidoDao;
import dao.ProductoDao;
import model.*;
import utils.JPAUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;


public class LoadRecords {

    public static void cargarRegistros() throws FileNotFoundException {
        EntityManager manager = JPAUtils.getEntityManager();
        CategoriaDao categoriaDao = new CategoriaDao(manager);
        ProductoDao productoDao = new ProductoDao(manager);
        ClienteDao clienteDao = new ClienteDao(manager);
        PedidoDao pedidoDao = new PedidoDao(manager);
        manager.getTransaction().begin();

        loadCategoria("categoria",categoriaDao,manager);

        loadProducto("producto",productoDao,categoriaDao,manager);

        loadCliente("cliente",clienteDao,manager);

        List<Cliente> clientesList = clienteDao.consultarTodos();
        List<Pedido> pedidoList= new ArrayList<>();

        for(Cliente cl:clientesList) {
            pedidoList.add(new Pedido(cl));
        }

        for(int i=0;i<pedidoList.size();i++) {
            pedidoList.get(i).agregarItems(new ItemsPedido(i+1,productoDao.buscarPorId((long) (i+1)),pedidoList.get(i)));
            pedidoDao.guardar(pedidoList.get(i));
        }

        manager.getTransaction().commit();
        manager.close();

    }

    private static void loadProducto(String type, ProductoDao productoDao,CategoriaDao categoriaDao, EntityManager manager) throws FileNotFoundException {
        List<String> productosTxt =readFile(type);
        for(int i=0;i<productosTxt.size();i++) {
            String[] line = productosTxt.get(i).split(";");
            if(line.length>1) {
                Categoria categoria=categoriaDao.consultaPorNombre(line[3]);
                Producto producto = new Producto(line[4],line[0],new BigDecimal(line[1]),categoria);
                productoDao.guardar(producto);
                manager.flush();
            }
        }
    }

    private static void loadCategoria(String type, CategoriaDao categoriaDao,EntityManager manager) throws FileNotFoundException {
        List<String> categoriasTxt =readFile(type);
        for(int i=0;i<categoriasTxt.size();i++) {
            String[] line = categoriasTxt.get(i).split(";");
            if(line.length==1) {
                Categoria categoria = new Categoria(categoriasTxt.get(i));
                categoriaDao.guardar(categoria);
                manager.flush();
            }
        }
    }

    private static void loadCliente(String type, ClienteDao clienteDao,EntityManager manager) throws FileNotFoundException {
        List<String> clientesTxt =readFile(type);
        for(int i=0;i<clientesTxt.size();i++) {
            String[] line = clientesTxt.get(i).split("~");
            System.out.println(line[0]+line[1]);
            if(line.length>1) {
                Cliente cliente= new Cliente(line[0],line[1]);
                clienteDao.guardar(cliente);
                manager.flush();
            }
        }
    }

    private static List<String> readFile(String type) throws FileNotFoundException {
        File file = new File("D:\\pruebas\\Alura\\JPAA\\"+type+".txt");
        Scanner scan = new Scanner(file);
        List<String> pedido= new ArrayList<>();
        while(scan.hasNextLine()){
            pedido.add(scan.nextLine());
        }
        scan.close();
        return pedido;
    }
}
