package dao;

import model.Categoria;

import javax.persistence.EntityManager;

public class CategoriaDao {

    private EntityManager manager;

    public CategoriaDao(EntityManager manager) {
        this.manager = manager;
    }

    public void guardar(Categoria categoria){

        this.manager.persist(categoria);
    }

    public void actualizar(Categoria categoria){

        this.manager.merge(categoria);
    }

    public void eliminar(Categoria categoria){
        categoria = this.manager.merge(categoria);
        this.manager.remove(categoria);
    }

    public Categoria consultaPorNombre(String nombre){
        String jpql =" SELECT C FROM Categoria AS C WHERE C.nombre=:nombre ";
        return manager.createQuery(jpql,Categoria.class).setParameter("nombre", nombre).getSingleResult();
    }
}
