package com.restorun.backendapplication.controller;

import com.restorun.backendapplication.model.Chef;
import com.restorun.backendapplication.service.ChefService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/chef")
@Api(tags = "Chef Controller")
public class ChefController {

    private final ChefService chefService;

    @Autowired
    public ChefController(ChefService chefService) {
        this.chefService = chefService;
    }

    @GetMapping("/retrieveChefById")
    public ResponseEntity<Chef> retrieveChefById(@RequestBody Long id) {
        Optional<Chef> chef = Optional.ofNullable(chefService.retrieveChefById(id));
        if (chef.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(chef.get());
    }

    @DeleteMapping("/deleteChef")
    public ResponseEntity<String> deleteChef(@RequestBody Long id) {
        Optional<Chef> chef = Optional.ofNullable(chefService.retrieveChefById(id));
        if (chef.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = chefService.deleteChef(id);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Chef deleted successfully");
    }

    @PostMapping("/saveChef")
    public ResponseEntity<String> saveChef(@RequestBody Chef chef) {
        boolean saved = chefService.saveChef(chef);
        if (!saved) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Chef saved successfully");
    }

}
