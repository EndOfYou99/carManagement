package com.example.car.management.filters;

import java.time.LocalDate;

public class MaintenanceFilter {

    private Long carId;
    private Integer garageId;
    private LocalDate startDate;
    private LocalDate endDate;

    public MaintenanceFilter() {
    }
    
    public MaintenanceFilter(Long carId, Integer garageId, LocalDate startDate, LocalDate endDate) {
        this.carId = carId;
        this.garageId = garageId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Integer getGarageId() {
        return garageId;
    }

    public void setGarageId(Integer garageId) {
        this.garageId = garageId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
