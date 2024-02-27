package com.restorun.backendapplication;

import com.restorun.backendapplication.enums.PaymentStatus;
import com.restorun.backendapplication.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@RestController
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @GetMapping
    public Order test(){
        Restaurant restaurant = new Restaurant();
        Set<MenuItem> menuItems = new HashSet<>();
        Menu menu = new Menu(1L, "debug_menu", "descmenu", restaurant, menuItems);
        List<Meal> meals = new ArrayList<Meal>();
        meals.add(new Meal(1L, "pizza", "nice", 15.0, menu));
        return new Order(1L, meals, 20.0, PaymentStatus.PAID , new DiningTable(), new Kitchen());
    }
}
