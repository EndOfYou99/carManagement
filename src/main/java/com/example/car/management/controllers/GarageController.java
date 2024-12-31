package com.example.car.management.controllers;

import com.example.car.management.dto.GarageDto;
import com.example.car.management.dto.CreateGarageDto;
import com.example.car.management.dto.GarageDailyAvailabilityReportDto;
import com.example.car.management.filters.GarageReportFilter;
import com.example.car.management.models.Garage;
import com.example.car.management.services.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/garages")
public class GarageController {

    @Autowired
    private GarageService garageService;

    @GetMapping
    public ResponseEntity<List<GarageDto>> getGarages(@RequestParam(required = false) String city) {
        try {
            List<GarageDto> garages = garageService.getGarages(city);
            return ResponseEntity.ok(garages);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GarageDto> getGarage(@PathVariable Long id) {
        try {
            GarageDto garage = garageService.getGarageById(id);
            return ResponseEntity.ok(garage);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GarageDto> updateGarage(@PathVariable Long id, @RequestBody CreateGarageDto garageDto) {
        try {
        	GarageDto updatedGarage = garageService.updateGarage(id, garageDto);
        	if(updatedGarage.getId()==null) {
                return ResponseEntity.notFound().build();
        	}
            return ResponseEntity.ok().body(updatedGarage);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<GarageDto> createGarage(@RequestBody CreateGarageDto garageDto) {
        try {
            GarageDto createdGarage = garageService.createGarage(garageDto);
            return ResponseEntity.created(null).body(createdGarage);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGarage(@PathVariable Long id) {
        try {
        	GarageDto garageToDelete = garageService.getGarageById(id);
            garageService.deleteGarage(id);
        	if(garageToDelete.getId()==null) {
                return ResponseEntity.notFound().build();
        	}
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/dailyAvailabilityReport")
    public ResponseEntity<List<GarageDailyAvailabilityReportDto>> getGarageReport(@RequestParam Long garageId, @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate startDate,@RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate endDate) {
        try {
        	GarageReportFilter filter = new GarageReportFilter();
        	filter.setGarageId(garageId);
        	filter.setStartDate(startDate);
        	filter.setEndDate(endDate);
            List<GarageDailyAvailabilityReportDto> reports = garageService.getDailyAvailabilityReport(filter);
            return ResponseEntity.ok(reports);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
