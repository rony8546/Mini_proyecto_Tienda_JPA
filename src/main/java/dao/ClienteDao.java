package dao;

import model.Cliente;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

public class ClienteDao {

    private EntityManager manager;

    public ClienteDao(EntityManager em) {
        this.manager = em;
    }

    public void guardar(Cliente cliente) {
        this.manager.persist(cliente);
    }


    public void actualizar(Cliente cliente) {
        this.manager.merge(cliente);
    }

    public void remover(Cliente cliente) {
        cliente = this.manager.merge(cliente);
        this.manager.remove(cliente);
    }

    public Cliente consultaPorId(Long id) {
        return manager.find(Cliente.class, id);
    }

    public List<Cliente> consultarTodos() {
        String jqpl = "SELECT P FROM Cliente AS P";
        return manager.createQuery(jqpl, Cliente.class).getResultList();
    }

    public List<Cliente> consultaPorNombre(String nombre) {
        String jpql = " SELECT P FROM Cliente AS P WHERE P.nombre=:nombre ";
        return manager.createQuery(jpql, Cliente.class).setParameter("nombre", nombre).getResultList();
    }

    public List<Cliente> consultaPorNombreDeCategoria(String nombre) {
        String jpql = "SELECT p FROM Cliente AS p WHERE p.categoria.nombre=:nombre";
        return manager.createQuery(jpql, Cliente.class).setParameter("nombre", nombre).getResultList();
    }

    public BigDecimal consultarPrecioPorNombreDeProducto(String nombre) {
        String jpql = "SELECT P.precio FROM Cliente AS P WHERE P.nombre=:nombre";
        return manager.createQuery(jpql, BigDecimal.class).setParameter("nombre", nombre).getSingleResult();
    }

}
