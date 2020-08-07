package epicentr.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {
    @GetMapping("/products")
    public String list(){
        return "products";
    }
    @GetMapping("/product-view/{product_id}")
    public String view(@PathVariable("product_id") int product_id,Model model){
        model.addAttribute("product_id",product_id);
        return "productview";
    }
}

