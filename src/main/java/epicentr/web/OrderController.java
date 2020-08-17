package epicentr.web;
import epicentr.entities.Message;
import epicentr.entities.Order;
import epicentr.entities.User;
import epicentr.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;
    @Autowired
    private ProductRespository productRespository;
    @GetMapping("/myorders")
    public String myorders(Model model)
    {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        String u =  a.getName();
        User user = userRepository.findByEmail(u);
        List<Order> orders = user.getOrders();
        for (int i=0;i<orders.size();i++) {
            int s =orders.get(i).getProduct().getProductImages().size();
            orders.get(i).getProduct().setOrders(null);
            orders.get(i).getOrderStatus().setOrders(null);
            for (int j = 0; j<s;j++){
                orders.get(i).getProduct().getProductImages().get(j).setProduct(null);
            }
        }
        model.addAttribute("orders",orders );
        return "myorders";
    }
    @PostMapping("/order")
    public String GetProfile(Order model)
    {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if(a.isAuthenticated() && a.getName()!="anonymousUser"){
            String u =  a.getName();
            User user = userRepository.findByEmail(u);
            Order o = new Order();
            o.setUser(user);
            o.setCount(model.getCount());
            o.setOrderStatus(orderStatusRepository.getOne(new Long(1)));
            o.setProduct(productRespository.getOne(model.getId()));
            orderRepository.save(o);
        }else {
            Order o = new Order();
            o.setCount(model.getCount());
            o.setCity(model.getCity());
            o.setName(model.getName());
            o.setLastName(model.getLastName());
            o.setPostOffice(model.getPostOffice());
            o.setOrderStatus(orderStatusRepository.getOne(new Long(1)));
            o.setProduct(productRespository.getOne(model.getId()));
            orderRepository.save(o);
        }


        return "redirect:/products";
    }
}
