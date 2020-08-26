package epicentr.web;

import epicentr.entities.Order;
import epicentr.entities.Product;
import epicentr.entities.User;
import epicentr.repositories.*;
import epicentr.services.StorageService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

@Controller
public class AdminController {
    private final StorageService storageService;

    @Autowired
    public AdminController(StorageService storageService) {
        this.storageService = storageService;
    }
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRespository productRespository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @GetMapping("admin/home")
    public String GetProfile(Model model) {
        List<Order> orders = orderRepository.findAll();
        for (Order o:orders){
            o.setOrderStatus(null);
            o.setProduct(null);
            o.setUser(null);
        }

        model.addAttribute("orders", orders);
        return "adminhome";
    }
    @PostMapping("order/change")
    public String ChangeOrderStatus(Order model) {
        Order o = orderRepository.findById(model.getId()).get();
        o.setOrderStatus(orderStatusRepository.findById(model.getOrderStatus().getId()).get());
        orderRepository.save(o);
        return "redirect:/admin/orders";
    }
    @PostMapping("product/delete")
    public String DeleteProduct(Product model) {
        productRespository.delete(productRespository.findById(model.getId()).get());
        return "redirect:/admin/products";
    }
    @PostMapping("document/delete")
    public String DeleteDocument(Product model) {
        documentRepository.delete(documentRepository.findById(model.getId()).get());
        return "redirect:/admin/documents";
    }
    @GetMapping("admin/orders")
    public String GetOrders(Model model) {
        List<Order> orders = orderRepository.findAll();

        model.addAttribute("orderStatuses",orderStatusRepository.findAll());
        model.addAttribute("orders", orders);
        return "adminorders";
    }
    @GetMapping("admin/products")
    public String GetProducts(Model model) {
        model.addAttribute("products", productRespository.findAll());
        return "adminproducts";
    }
    @GetMapping("admin/documents")
    public String GetDocuments(Model model) {
        model.addAttribute("documents", documentRepository.findAll());
        return "admindocuments";
    }
}
