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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name="category_id", referencedColumnName = "id",insertable = false, updatable = false)
    private Category category;
    @OneToMany(targetEntity=ProductImages.class, mappedBy="product",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImages> productImages = new ArrayList<>();
    @OneToMany(targetEntity=Order.class, mappedBy="product",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
    private String name;

    private String description;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    private BigDecimal price;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public List<ProductImages> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImages> productImages) {
        this.productImages = productImages;
    }
}
