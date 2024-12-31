package com.example.car.management.controllers;

import com.example.car.management.dto.CreateMaintenanceDto;
import com.example.car.management.dto.MaintenanceDto;
import com.example.car.management.models.MonthlyMaintenanceReport;
import com.example.car.management.filters.MaintenanceFilter;
import com.example.car.management.filters.MaintenanceMonthlyReportFilter;
import com.example.car.management.services.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    @GetMapping
    public ResponseEntity<List<MaintenanceDto>> getMaintenances(
            @RequestParam(required = false) Long carId,
            @RequestParam(required = false) Long garageId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate  startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate  endDate) {
        try {
            MaintenanceFilter filter = new MaintenanceFilter(carId, garageId, startDate, endDate);
            List<MaintenanceDto> maintenances = maintenanceService.getMaintenances(filter);
            return ResponseEntity.ok(maintenances);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceDto> getMaintenance(@PathVariable Long id) {
        try {
            MaintenanceDto maintenance = maintenanceService.getMaintenanceById(id);
            return ResponseEntity.ok(maintenance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<MaintenanceDto> createMaintenance(@RequestBody CreateMaintenanceDto maintenanceDto) {
        try {
            MaintenanceDto createdMaintenance = maintenanceService.createMaintenance(maintenanceDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMaintenance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMaintenance(@PathVariable Long id, @RequestBody CreateMaintenanceDto maintenanceDto) {
        try {
            maintenanceService.updateMaintenance(id, maintenanceDto);
            
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable Long id) {
        try {
            maintenanceService.deleteMaintenance(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @GetMapping("/monthlyRequestsReport")
    public ResponseEntity<List<MonthlyMaintenanceReport>> getMonthlyReport(
            @RequestParam Long garageId,
            @RequestParam String startMonth,
            @RequestParam String endMonth) {
        try {
            // Append "-01" to match yyyy-MM-dd format
            LocalDate start = LocalDate.parse(startMonth + "-01"); // Default ISO_LOCAL_DATE formatter
            LocalDate end = LocalDate.parse(endMonth + "-01");     // Default ISO_LOCAL_DATE formatter

            MaintenanceMonthlyReportFilter filter = new MaintenanceMonthlyReportFilter(garageId, start, end);
            List<MonthlyMaintenanceReport> reports = maintenanceService.getMonthlyReport(filter);
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
