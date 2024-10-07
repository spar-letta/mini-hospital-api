package com.javenock.doctor_service.repository;

import com.javenock.doctor_service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
