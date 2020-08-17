package epicentr.web;

import epicentr.repositories.ProductRespository;
import epicentr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {
    @Autowired
    private ProductRespository productRespository;
    @GetMapping("/products")
    public String list(){
        return "products";
    }
    //Old on Vue.js
//    @GetMapping("/product-view/{product_id}")
//    public String view(@PathVariable("product_id") int product_id,Model model){
//        model.addAttribute("product_id",product_id);
//        return "productview";
//    }
    //New on HTML
    @GetMapping("/product-view/{product_id}")
    public String view(@PathVariable("product_id") Long product_id,Model model){
        model.addAttribute("product",productRespository.findById(product_id).get());
        return "productviewHTML";
    }

}

