package epicentr.web;

import com.google.gson.Gson;
import epicentr.entities.Order;
import epicentr.entities.Product;
import epicentr.entities.User;
import epicentr.models.ShortProductModel;
import epicentr.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
    public String myorders(Model model) {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        String u = a.getName();
        User user = userRepository.findByEmail(u);
        List<Order> orders = user.getOrders();
        for (int i = 0; i < orders.size(); i++) {
            int s = orders.get(i).getProduct().getProductImages().size();
            orders.get(i).getProduct().setOrders(null);
            orders.get(i).getOrderStatus().setOrders(null);
            for (int j = 0; j < s; j++) {
                orders.get(i).getProduct().getProductImages().get(j).setProduct(null);
            }
        }
        model.addAttribute("orders", orders);
        return "user/orders/view";
    }

    //    @GetMapping("/addtocart/{id}")
//    public String AddIntoCart(@PathVariable Long id, HttpServletResponse response, HttpServletRequest request)
//    {
//        Cookie oldValue= WebUtils.getCookie(request, "value");
//        List<ShortProductModel> products=new ArrayList<>();
//        Gson gson = new GsonBuilder().create();;
//        if(oldValue!=null) {
//            Type type = new TypeToken<ArrayList<ShortProductModel>>(){}.getType();
//            products = gson.fromJson(oldValue.getValue(), type);
//        }
//        Product newProd=productRespository.findById(id).get();
//        products.add(new ShortProductModel(newProd.getId(),newProd.getName(),newProd.getDiscount(),newProd.getDescription()));
//
//        String json=gson.toJson(products);
//        Cookie cookie = new Cookie("value", json);
//        cookie.setMaxAge(7 * 24 * 60 * 60);
//        response.addCookie(cookie);
//        return "redirect:/products";
//    }
    @GetMapping("/cart")
    public String Cart(HttpServletRequest request, Model model) {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a.isAuthenticated() && a.getName() != "anonymousUser") {
            Cookie value = WebUtils.getCookie(request, "cart");
            Cookie[] cookies = request.getCookies();
            if (value != null) {
                Gson gson = new Gson();
                //Type type = new TypeToken<ArrayList<ShortProductModel>>(){}.getType();
                //List<ShortProductModel> list = gson.fromJson(value.getValue(), type);
                String[] arr = gson.fromJson(value.getValue(), String[].class);
                List<ShortProductModel> products = new ArrayList<>();
                for (String item : arr) {
                    Product prod = productRespository.findById(Long.parseLong(item)).get();
                    if (prod != null) {
                        products.add(new ShortProductModel(
                                prod.getId(),
                                prod.getName(),
                                prod.getDiscount(),
                                prod.getPrice(),
                                prod.getProductImages().get(0).getImage_name()));
                    }
                }
                model.addAttribute("products", products);
            }
        }
        return "user/carts/cart";
    }

    @PostMapping("/cart-order")
    public String OrderFromCart(@RequestParam(name = "productId[]") long[] ids, @RequestParam(name = "productCount[]") int[] counts, HttpServletResponse response) {
        if (ids != null && counts != null) {
            Authentication a = SecurityContextHolder.getContext().getAuthentication();
            String u = a.getName();
            User user = userRepository.findByEmail(u);
            for (int i = 0; i < ids.length; i++) {
                Order o = new Order();
                o.setUser(user);
                o.setCount(counts[i]);
                o.setOrderStatus(orderStatusRepository.getOne(new Long(1)));
                o.setProduct(productRespository.getOne(ids[i]));
                orderRepository.save(o);
            }

            Cookie cookie = new Cookie("cart", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);

            return "redirect:/myorders";
        } else return "redirect:/cart";
    }

    @PostMapping("/order")
    public String GetProfile(Order model) {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a.isAuthenticated() && a.getName() != "anonymousUser") {
            String u = a.getName();
            User user = userRepository.findByEmail(u);
            Order o = new Order();
            o.setUser(user);
            o.setCount(model.getCount());
            o.setOrderStatus(orderStatusRepository.getOne(new Long(1)));
            o.setProduct(productRespository.getOne(model.getId()));
            orderRepository.save(o);
        } else {
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
