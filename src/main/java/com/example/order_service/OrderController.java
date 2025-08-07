package com.example.order_service;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final RestaurantClient restaurantClient;

    public OrderController(OrderRepository orderRepository, RestaurantClient restaurantClient) {
        this.orderRepository = orderRepository;
        this.restaurantClient = restaurantClient;
    }

    @PostMapping("/placeOrder")
    public Order placeOrder(@RequestBody Map<String,String> payload){
            String restaurantName = payload.get("restaurantName");
        List<Map<String,Object>> restaurants = restaurantClient.getAllRestaurants();
        Map<String,Object> selectedRestaurant = restaurants.stream().filter(
                r->r.get("name").toString().equalsIgnoreCase(restaurantName)
        ).findFirst().orElseThrow(()->new RuntimeException("Restaurant not found"));

   Order order=new Order();
   order.setRestaurantName(selectedRestaurant.get("name").toString());
   order.setCuisine(selectedRestaurant.get("cuisine").toString());
   order.setStatus("Placed");
   return orderRepository.save(order);
    }

    @GetMapping("/getAllOrders")
    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }

}

