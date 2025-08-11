package com.example.order_service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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


    @CircuitBreaker(name = "restaurantCB", fallbackMethod = "fallbackPlaceOrder")
    @PostMapping("/placeOrder")
    public Order placeOrder(@RequestBody Map<String,String> payload){
            String restaurantName = payload.get("restaurantName");
        List<Restaurant> restaurants = restaurantClient.getAllRestaurants();
        Restaurant selectedRestaurant = restaurants.stream().filter(
                r->r.getName().equalsIgnoreCase(restaurantName)
        ).findFirst().orElseThrow(()->new RuntimeException("Restaurant not found"));

   Order order=new Order();
   order.setRestaurantName(selectedRestaurant.getName());
   order.setCuisine(selectedRestaurant.getCuisine());
   order.setStatus("Placed");
   return orderRepository.save(order);
    }

    @GetMapping("/getAllOrders")
    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }

    public Order fallbackPlaceOrder(Map<String,String> payload, Throwable t) {
        System.out.println("Fallback triggered due to: " + t.getMessage());
        Order order = new Order();
        order.setRestaurantName("Fallback Restaurant");
        order.setCuisine("Unavailable");
        order.setStatus("Pending");
        return order;
    }


}

