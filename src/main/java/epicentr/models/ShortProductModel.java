package epicentr.models;

import java.math.BigDecimal;

public class ShortProductModel {
    public Long id;
    public String name;
    public Integer discount;
    public String description;
    public BigDecimal price;
    public String image_name;

    public ShortProductModel(Long id, String name, Integer discount, String description, BigDecimal price,String image_name) {
        this.id = id;
        this.name = name;
        this.discount = discount;
        this.description = description;
        this.price = price;
        this.image_name=image_name;
    }
}
