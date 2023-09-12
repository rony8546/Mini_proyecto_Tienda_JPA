package dao;

import model.Pedido;
import vo.RelatorioDeVenta;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDao {

    private EntityManager manager;

    public PedidoDao(EntityManager manager) {
        this.manager = manager;
    }

    public void guardar(Pedido pedido) {
        this.manager.persist(pedido);
    }


    public void actualizar(Pedido pedido) {
        this.manager.merge(pedido);
    }

    public void remover(Pedido pedido) {
        pedido=this.manager.merge(pedido);
        this.manager.remove(pedido);
    }

    public Pedido consultaPorId(Long id) {
        return manager.find(Pedido.class, id);
    }

    public List<Pedido> consultarTodos(){
        String jqpl= "SELECT P FROM Pedido AS P";
        return manager.createQuery(jqpl,Pedido.class).getResultList();
    }

    public BigDecimal valorTotalVendido() {
        String jpql= "SELECT SUM(p.valorTotal) FROM Pedido p";
        return manager.createQuery(jpql,BigDecimal.class).getSingleResult();
    }

    public Double valorPromedioVendido() {
        String jpql= "SELECT AVG(p.valorTotal) FROM Pedido p";
        return manager.createQuery(jpql,Double.class).getSingleResult();
    }

    public List<Object[]> relatorioDeVentas(){
        String jpql="SELECT producto.nombre, "
                + "SUM(item.cantidad), "
                + "MAX(pedido.fecha) "
                + "FROM Pedido pedido "
                + "JOIN pedido.items item "
                + "JOIN item.producto producto "
                + "GROUP BY producto.nombre "
                + "ORDER BY item.cantidad DESC";
        return manager.createQuery(jpql,Object[].class).getResultList();
    }

    public List<RelatorioDeVenta> relatorioDeVentasVO(){
        String jpql="SELECT new vo.RelatorioDeVenta (producto.nombre, "
                + "SUM(item.cantidad), "
                + "MAX(pedido.fecha)) "
                + "FROM Pedido pedido "
                + "JOIN pedido.items item "
                + "JOIN item.producto producto "
                + "GROUP BY producto.nombre "
                + "ORDER BY item.cantidad DESC";
        return manager.createQuery(jpql,RelatorioDeVenta.class).getResultList();
    }

    public Pedido consultarPedidoConCliente(Long id) {
        String jpql="SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id=:id";
        return manager.createQuery(jpql,Pedido.class).setParameter("id", id).getSingleResult();
    }

}
