package com.hellokoding.springboot.restful.product;

import com.hellokoding.springboot.restful.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRespository extends JpaRepository<Product, Long> {
}
