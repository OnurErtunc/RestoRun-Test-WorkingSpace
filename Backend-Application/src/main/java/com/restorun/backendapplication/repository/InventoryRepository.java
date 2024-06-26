package com.restorun.backendapplication.repository;

import com.restorun.backendapplication.model.Inventory;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
