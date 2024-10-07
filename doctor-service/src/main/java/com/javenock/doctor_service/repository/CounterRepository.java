package com.javenock.doctor_service.repository;

import com.javenock.doctor_service.model.Counter;
import com.javenock.doctor_service.model.dataType.CounterType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CounterRepository extends JpaRepository<Counter, Long> {

    Optional<Counter> findByCounterType(CounterType counterType);
}
