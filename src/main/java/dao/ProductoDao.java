package dao;

import model.Producto;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProductoDao {

    private EntityManager manager;

    public ProductoDao(EntityManager manager) {
        this.manager = manager;
    }

    public void guardar(Producto producto) {
        this.manager.persist(producto);
    }

    public Producto buscarPorId(Long id) {
        return this.manager.find(Producto.class, id);
    }

    public List<Producto> buscarTodos() {
        String query = "SELECT p FROM Producto p";
        return this.manager.createQuery(query, Producto.class).getResultList();
    }

    public List<Producto> buscarPorNombre(String nombre) {
        String query = "SELECT p FROM Producto p WHERE p.nombre = :nombre";
        return this.manager.createQuery(query, Producto.class)
                .setParameter("nombre", nombre)
                .getResultList();
    }

    public List<Producto> buscarPorNombreDeCategoria(String nombre) {
        String query = "SELECT p FROM Producto p WHERE p.categoria.nombre = :nombre";
        return this.manager.createQuery(query, Producto.class)
                .setParameter("nombre", nombre)
                .getResultList();
    }

    public BigDecimal ConsultarPrecioPorNombreDeProducto(String nombre) {
        String query = "SELECT p.precio FROM Producto p WHERE p.nombre = :nombre";
        return this.manager.createQuery(query, BigDecimal.class)
                .setParameter("nombre", nombre)
                .getSingleResult();
    }

    public List<Producto> consultarPorParametros(String nombre, BigDecimal precio, LocalDate fecha){  //consultas dinamicas con JPQL
        StringBuilder jpql=new StringBuilder("SELECT p FROM Producto p WHERE 1=1 ");

        if(nombre!=null && !nombre.trim().isEmpty()) {
            jpql.append("AND p.nombre=:nombre ");
        }
        if(precio!=null && !precio.equals(new BigDecimal(0))) {
            jpql.append("AND p.precio=:precio ");
        }
        if(fecha!=null) {
            jpql.append("AND p.fechaDeRegistro=:fecha");
        }
        TypedQuery<Producto> query = manager.createQuery(jpql.toString(),Producto.class);
        if(nombre!=null && !nombre.trim().isEmpty()) {
            query.setParameter("nombre", nombre);
        }
        if(precio!=null && !precio.equals(new BigDecimal(0))) {
            query.setParameter("precio", precio);
        }
        if(fecha!=null) {
            query.setParameter("fechaDeRegistro", fecha);
        }

        return query.getResultList();
    }


    public List<Producto> consultarPorParametrosConAPICriteria(String nombre, BigDecimal precio,LocalDate fecha){  //consultas dinamicas con API Criteria
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Producto> query = builder.createQuery(Producto.class);
        Root<Producto> from = query.from(Producto.class);


        Predicate filtro = builder.and();
        if(nombre!=null && !nombre.trim().isEmpty()) {
            filtro=builder.and(filtro,builder.equal(from.get("nombre"), nombre));
        }
        if(precio!=null && !precio.equals(new BigDecimal(0))) {
            filtro=builder.and(filtro,builder.equal(from.get("precio"), precio));
        }
        if(fecha!=null) {
            filtro=builder.and(filtro,builder.equal(from.get("fechaDeRegistro"), fecha));
        }

        query=query.where(filtro);
        return manager.createQuery(query).getResultList();


    }
}
