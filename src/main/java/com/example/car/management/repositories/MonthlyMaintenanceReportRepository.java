package com.example.car.management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.car.management.models.MonthlyMaintenanceReport;

public interface MonthlyMaintenanceReportRepository extends JpaRepository<MonthlyMaintenanceReport, Long> {
}

