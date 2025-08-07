package com.example.order_service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name ="restaurant-service")
public interface RestaurantClient {

    @GetMapping("/restaurants/getRestaurant")
    List<Map<String,Object>> getAllRestaurants();
}
