package epicentr.services;

import epicentr.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import epicentr.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public List<Product> findAll();

    public Optional<Product> findById(Long id);

    public Product save(Product stock);

    public void deleteById(Long id);
}
