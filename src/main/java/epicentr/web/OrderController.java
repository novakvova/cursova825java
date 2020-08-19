package epicentr.web;
//import com.google.gson.Gson;
import epicentr.entities.Order;
import epicentr.entities.User;
import epicentr.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.WebUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @GetMapping("/cart")
    public String Cart(HttpServletResponse response, HttpServletRequest request, Model model){
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if(a.isAuthenticated() && a.getName()!="anonymousUser") {
            Cookie value=WebUtils.getCookie(request, "value");
            if(value!=null) {


                model.addAttribute("values",value);

                int[] numbers = {1, 1, 2, 3, 5, 8, 13};

                //Gson gson = new Gson();

                //String numbersJson = gson.toJson(numbers);
//                Cookie cookie = new Cookie("value",numbersJson);
//                cookie.setMaxAge(7 * 24 * 60 * 60);
//                response.addCookie(cookie);
            }
            else model.addAttribute("error","Ваш кошик пустий");
        }
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
