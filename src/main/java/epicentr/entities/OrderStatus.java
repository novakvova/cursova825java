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

    @UpdateTimestamp
    private Date updatedAt;

}
