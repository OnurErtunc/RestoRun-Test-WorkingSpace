package com.restorun.backendapplication.repository;

import com.restorun.backendapplication.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
