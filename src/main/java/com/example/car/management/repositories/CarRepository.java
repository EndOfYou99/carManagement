package com.example.car.management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.car.management.models.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
