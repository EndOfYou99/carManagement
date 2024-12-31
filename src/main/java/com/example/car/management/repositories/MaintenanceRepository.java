package com.example.car.management.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.car.management.models.Maintenance;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
	
	   @Query("SELECT m FROM Maintenance m WHERE m.garageId = :garageId AND m.scheduledDate BETWEEN :startDate AND :endDate")
	    List<Maintenance> findByGarageIdAndScheduledDateBetween(
	            @Param("garageId") Long garageId,
	            @Param("startDate") LocalDate startDate,
	            @Param("endDate") LocalDate endDate
	    );
}
