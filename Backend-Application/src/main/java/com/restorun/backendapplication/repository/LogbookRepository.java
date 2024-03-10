package com.restorun.backendapplication.repository;

import com.restorun.backendapplication.model.Logbook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogbookRepository extends JpaRepository<Logbook, Long> {
}
