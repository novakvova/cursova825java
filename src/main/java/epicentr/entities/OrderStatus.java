package epicentr.entities;


        import lombok.Data;
        import org.hibernate.annotations.CreationTimestamp;
        import org.hibernate.annotations.UpdateTimestamp;

        import javax.persistence.*;
        import java.math.BigDecimal;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;

@Entity

@Data
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(targetEntity=Order.class, mappedBy="orderStatus",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
    @CreationTimestamp
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @UpdateTimestamp
    private Date updatedAt;

}
