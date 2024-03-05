package com.restorun.backendapplication.repository;
import com.restorun.backendapplication.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
