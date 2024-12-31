package com.example.car.management.services;

import com.example.car.management.dto.GarageDto;
import com.example.car.management.dto.CreateGarageDto;
import com.example.car.management.dto.DtoFactory;
import com.example.car.management.dto.GarageDailyAvailabilityReportDto;
import com.example.car.management.filters.GarageReportFilter;
import com.example.car.management.models.Car;
import com.example.car.management.models.Garage;
import com.example.car.management.models.Maintenance;
import com.example.car.management.repositories.GarageRepository;
import com.example.car.management.repositories.MaintenanceRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GarageService {

    @Autowired
    private GarageRepository garageRepository;
    
    @Autowired MaintenanceRepository maintenanceRepository;

    public List<GarageDto> getGarages(String city) {
        List<Garage> garages = garageRepository.findAll();
        if (city != null && !city.isEmpty()) {
            garages = garages.stream()
                    .filter(g -> g.getCity().toLowerCase().contains(city.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return garages.stream().map(DtoFactory::asDto).collect(Collectors.toList());
    }

    public GarageDto getGarageById(Long id) {
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Garage not found"));
        return DtoFactory.asDto(garage);
    }

    public GarageDto updateGarage(Long id, CreateGarageDto garageDto) {
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Garage not found"));
        garage.setName(garageDto.getName());
        garage.setLocation(garageDto.getLocation());
        garage.setCity(garageDto.getCity());
        garage.setCapacity(garageDto.getCapacity());
        garage = garageRepository.save(garage);
        return DtoFactory.asDto(garage);
    }

    public GarageDto createGarage(CreateGarageDto garageDto) {
        Garage garage = new Garage();
        garage.setName(garageDto.getName());
        garage.setLocation(garageDto.getLocation());
        garage.setCity(garageDto.getCity());
        garage.setCapacity(garageDto.getCapacity());
        garage = garageRepository.save(garage);
        return DtoFactory.asDto(garage);
    }

    @Transactional
    public void deleteGarage(Long id) {
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Garage not found"));

        for (Car car : garage.getCars()) {
            car.getGarages().remove(garage);
        }
        garage.getCars().clear();

        garageRepository.delete(garage);
    }

    public List<GarageDailyAvailabilityReportDto> getDailyAvailabilityReport(GarageReportFilter filter) {
        Garage garage = garageRepository.findById(filter.getGarageId())
                .orElseThrow(() -> new IllegalArgumentException("Garage not found"));

        List<Maintenance> maintenances = maintenanceRepository.findByGarageIdAndScheduledDateBetween(
                filter.getGarageId(),
                filter.getStartDate(),
                filter.getEndDate()
        );

        return maintenances.stream()
                .collect(Collectors.groupingBy(Maintenance::getScheduledDate))
                .entrySet().stream()
                .map(entry -> {
                    LocalDate date = entry.getKey();
                    int requestCount = entry.getValue().size();
                    return new GarageDailyAvailabilityReportDto(
                            date,
                            requestCount,
                            garage.getCapacity() - requestCount
                    );
                })
                .collect(Collectors.toList());
    }
}