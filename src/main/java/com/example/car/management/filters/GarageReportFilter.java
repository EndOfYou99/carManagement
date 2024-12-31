package com.example.car.management.filters;

import java.time.LocalDate;

public class GarageReportFilter {

    private Long garageId;
    private LocalDate startDate;
    private LocalDate endDate;

    public GarageReportFilter() {
    }

    public GarageReportFilter(Long garageId, LocalDate startDate, LocalDate endDate) {
        this.garageId = garageId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getGarageId() {
        return garageId;
    }

    public void setGarageId(Long garageId) {
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

