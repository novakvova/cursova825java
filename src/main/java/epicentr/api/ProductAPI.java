package epicentr.api;

import epicentr.entities.Product;
import epicentr.entities.ProductImages;
import epicentr.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/products")
@Slf4j
@RequiredArgsConstructor
public class ProductAPI {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> r =productService.findAll();
        for (int i=0;i<r.size();i++) {
            if (r.get(i).getDiscount() == null) {
                r.remove(r.get(0));
            }
        }
        for (int i=0;i<r.size();i++) {
                int s =r.get(i).getProductImages().size();
                r.get(i).setOrders(null);
                for (int j = 0; j<s;j++){
                    r.get(i).getProductImages().get(j).setProduct(null);
                }
        }
        return ResponseEntity.ok(r);
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Optional<Product> stock = productService.findById(id);
        if (!stock.isPresent()) {
            //log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        for (int j = 0; j<stock.get().getProductImages().size();j+=1 ){
            stock.get().getProductImages().get(j).setProduct(null);
        }
        stock.get().setOrders(null);
        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody Product product) {
        if (!productService.findById(id).isPresent()) {
            //log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(productService.save(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (!productService.findById(id).isPresent()) {
            //log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        productService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
