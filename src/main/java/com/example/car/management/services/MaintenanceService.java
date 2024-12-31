package com.example.car.management.services;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.car.management.dto.CreateMaintenanceDto;
import com.example.car.management.dto.DtoFactory;
import com.example.car.management.dto.MaintenanceDto;
import com.example.car.management.filters.MaintenanceFilter;
import com.example.car.management.filters.MaintenanceMonthlyReportFilter;
import com.example.car.management.models.Car;
import com.example.car.management.models.Garage;
import com.example.car.management.models.Maintenance;
import com.example.car.management.models.MonthlyMaintenanceReport;
import com.example.car.management.repositories.CarRepository;
import com.example.car.management.repositories.GarageRepository;
import com.example.car.management.repositories.MaintenanceRepository;


@Service
public class MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private GarageRepository garageRepository;

    public List<MaintenanceDto> getMaintenances(MaintenanceFilter filter) {
        List<Maintenance> maintenances = maintenanceRepository.findAll();

        if (filter.getCarId() != null) {
            maintenances = maintenances.stream()
                    .filter(m -> m.getCar().getId().equals(filter.getCarId()))
                    .collect(Collectors.toList());
        }

        if (filter.getGarageId() != null) {
            maintenances = maintenances.stream()
                    .filter(m -> m.getGarage().getId().equals(filter.getGarageId()))
                    .collect(Collectors.toList());
        }

        if (filter.getStartDate() != null) {
            LocalDate startDate = filter.getStartDate();
            maintenances = maintenances.stream()
                    .filter(m -> !m.getScheduledDate().isBefore(startDate))
                    .collect(Collectors.toList());
        }

        if (filter.getEndDate() != null) {
            LocalDate endDate = (filter.getEndDate());
            maintenances = maintenances.stream()
                    .filter(m -> !m.getScheduledDate().isAfter(endDate))
                    .collect(Collectors.toList());
        }

        return maintenances.stream().map(DtoFactory::asDto).collect(Collectors.toList());
    }

    public MaintenanceDto getMaintenanceById(Long id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Maintenance not found"));
        return DtoFactory.asDto(maintenance);
    }

    public MaintenanceDto createMaintenance(CreateMaintenanceDto maintenanceDto) {
        Maintenance maintenance = new Maintenance();
        Car car = carRepository.findById(maintenanceDto.getCarId())
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));
        Garage garage = garageRepository.findById(maintenanceDto.getGarageId())
                .orElseThrow(() -> new IllegalArgumentException("Garage not found"));

        maintenance.setServiceType(maintenanceDto.getServiceType());
        maintenance.setScheduledDate(maintenanceDto.getScheduledDate());
        maintenance.setCar(car);
        maintenance.setCarId(car.getId());
        maintenance.setGarage(garage);
        maintenance.setGarageId(garage.getId());

        maintenance = maintenanceRepository.save(maintenance);
        return DtoFactory.asDto(maintenance);
    }

    public void updateMaintenance(Long id, CreateMaintenanceDto maintenanceDto) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Maintenance not found"));

        Car car = carRepository.findById(maintenanceDto.getCarId())
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));
        Garage garage = garageRepository.findById(maintenanceDto.getGarageId())
                .orElseThrow(() -> new IllegalArgumentException("Garage not found"));

        maintenance.setServiceType(maintenanceDto.getServiceType());
        maintenance.setScheduledDate(maintenanceDto.getScheduledDate());
        maintenance.setCar(car);
        maintenance.setGarage(garage);

        maintenanceRepository.save(maintenance);
    }

    public void deleteMaintenance(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new IllegalArgumentException("Maintenance not found");
        }
        maintenanceRepository.deleteById(id);
    }

    public List<MonthlyMaintenanceReport> getMonthlyReport(MaintenanceMonthlyReportFilter filter) {
        List<Maintenance> maintenances = maintenanceRepository.findByGarageIdAndScheduledDateBetween(
                filter.getGarageId(),
                filter.getStartMonth(),
                filter.getEndMonth()
        );

        return maintenances.stream()
                .collect(Collectors.groupingBy(m -> m.getScheduledDate().getMonth()))
                .entrySet().stream()
                .map(entry -> new MonthlyMaintenanceReport(entry.getKey().toString(), entry.getValue().size()))
                .collect(Collectors.toList());
    }
}
