package epicentr.api;

import epicentr.entities.Order;
import epicentr.models.CreateOrder;
import epicentr.models.JwtRequest;
import epicentr.models.JwtResponse;
import epicentr.repositories.OrderRepository;
import epicentr.repositories.OrderStatusRepository;
import epicentr.repositories.ProductRespository;
import epicentr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
