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
    @Column(nullable=true)
    private Integer discount;
    private String description;

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

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
