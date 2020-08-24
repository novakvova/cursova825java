package epicentr.web;

import epicentr.entities.Order;
import epicentr.entities.User;
import epicentr.repositories.OrderRepository;
import epicentr.repositories.OrderStatusRepository;
import epicentr.repositories.RoleRepository;
import epicentr.repositories.UserRepository;
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
    private UserRepository userRepository;

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
    @GetMapping("admin/orders")
    public String GetOrders(Model model) {
        List<Order> orders = orderRepository.findAll();

        model.addAttribute("orderStatuses",orderStatusRepository.findAll());
        model.addAttribute("orders", orders);
        return "adminorders";
    }
}
