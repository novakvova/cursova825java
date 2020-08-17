package epicentr.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;
    @OneToMany(targetEntity=Product.class, mappedBy="category",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}