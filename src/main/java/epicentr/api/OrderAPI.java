package epicentr.api;

import epicentr.entities.Order;
import epicentr.entities.User;
import epicentr.models.CreateOrder;
import epicentr.models.JwtRequest;
import epicentr.models.JwtResponse;
import epicentr.repositories.OrderRepository;
import epicentr.repositories.OrderStatusRepository;
import epicentr.repositories.ProductRespository;
import epicentr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderAPI {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRespository productRespository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<String> createOrder(@RequestBody CreateOrder model){
        Order o = new Order();
        o.setUser(userRepository.findByEmail(model.getUsername()));
        o.setOrderStatus(orderStatusRepository.getOne(new Long(1)));
        o.setCount(model.getCount());
        o.setProduct(productRespository.getOne(new Long(model.getProductId())));
        orderRepository.save(o);
        return ResponseEntity.ok("ok");
    }
    @RequestMapping(value = "/myorders", method = RequestMethod.POST)
    public ResponseEntity<List<Order>> myorders(@RequestBody CreateOrder model) {
//        Authentication a = SecurityContextHolder.getContext().getAuthentication();
//        String u = a.getName();
        User user = userRepository.findByEmail(model.getUsername());
        List<Order> orders = user.getOrders();
        for (int i = 0; i < orders.size(); i++) {
            int s = orders.get(i).getProduct().getProductImages().size();
            orders.get(i).getProduct().setOrders(null);
            orders.get(i).getOrderStatus().setOrders(null);
            orders.get(i).setUser(null);
            for (int j = 0; j < s; j++) {
                orders.get(i).getProduct().getProductImages().get(j).setProduct(null);
            }
        }

        return ResponseEntity.ok(orders);
    }
}
