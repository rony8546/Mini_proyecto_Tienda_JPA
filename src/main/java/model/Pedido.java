package model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha=LocalDate.now();

    private BigDecimal valorTotal = BigDecimal.ZERO;

    @ManyToOne(fetch=FetchType.LAZY)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido" , cascade = CascadeType.ALL)
    private List<ItemsPedido> items = new ArrayList<>();

    public Pedido() {
    }

    public void agregarItems(ItemsPedido item){
        this.items.add(item);
        item.setPedido(this);
        this.valorTotal=this.valorTotal.add(item.getvalor());
    }

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}