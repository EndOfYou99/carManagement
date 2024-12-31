package com.example.car.management.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.car.management.models.GarageReport;
import com.example.car.management.models.Maintenance;

public interface GarageReportRepository extends JpaRepository<GarageReport, Long> {
	
 
}
