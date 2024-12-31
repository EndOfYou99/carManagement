package com.example.car.management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.car.management.models.Garage;

public interface GarageRepository extends JpaRepository<Garage, Long> {
}

