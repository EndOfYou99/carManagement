package com.example.car.management.dto;

import com.example.car.management.models.*;
import java.util.stream.Collectors;

public class DtoFactory {

    public static CarDto asDto(Car car) {
        return new CarDto(
            car.getId(),
            car.getMake(),
            car.getModel(),
            car.getProductionYear(),
            car.getLicensePlate(),
            car.getGarages().stream().map(DtoFactory::asDto).collect(Collectors.toList()),
            car.getGarages().stream().map(Garage::getId).collect(Collectors.toList())
        );
    }

    public static GarageDto asDto(Garage garage) {
        return new GarageDto(
            garage.getId(),
            garage.getName(),
            garage.getLocation(),
            garage.getCity(),
            garage.getCapacity()
        );
    }

    public static MaintenanceDto asDto(Maintenance maintenance) {
        return new MaintenanceDto(
            maintenance.getId(),
            maintenance.getCar().getId(),
            maintenance.getCar().getModel(),
            maintenance.getServiceType(),
            maintenance.getScheduledDate().toLocalDate(),
            maintenance.getGarage().getId(),
            maintenance.getGarage().getName()
        );
    }
}
